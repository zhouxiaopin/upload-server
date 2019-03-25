package cn.gzticc.uploadserver.service.impl;

import cn.gzticc.uploadserver.common.ServerResponse;
import cn.gzticc.uploadserver.service.IFileService;
import cn.gzticc.uploadserver.utils.Base64Util;
import cn.gzticc.uploadserver.utils.FTPUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Slf4j
@Service
public class FileServiceImpl implements IFileService {

    @Value("${ftp.server.img.http.prefix}")
    private String ftpServerImgHttpPrefix;
    @Value("${ftp.server.video.http.prefix}")
    private String ftpServerVideoHttpPrefix;
    @Value("${us.picRemotePath}")
    private String picRemotePath;
    @Value("${us.videoRemotePath}")
    private String videoRemotePath;

    @Autowired
    private FTPUtil ftpUtil;

    @Override
    public ServerResponse imgUpload(String[] base64Strs, String path){
        List<MultipartFile> files = Lists.newArrayList();

        for(int i = 0, len = base64Strs.length; i < len; i++) {
            MultipartFile file = Base64Util.base64ToMultipart(base64Strs[i]);
            files.add(file);
        }
        return upload(files,path,ftpServerVideoHttpPrefix,videoRemotePath);

    }
    @Override
    public ServerResponse videoUpload(MultipartFile[] files, String path){
        return upload(Lists.newArrayList(files),path,ftpServerVideoHttpPrefix,videoRemotePath);
    }

    /**
     *
     * @param files 要上传的多个文件
     * @param path  图片缓存路径
     * @param HttpPrefix 上传成功后返回url的前缀
     * @param remotePath  上传到ftp的路径名
     * @return
     */
    private ServerResponse upload(List<MultipartFile> files, String path,String HttpPrefix,String remotePath){
        List<String> urls = Lists.newLinkedList();
        List<String> fileNames = Lists.newLinkedList();

        List<File> targetFiles = Lists.newLinkedList();

        for(int i = 0, len = files.size(); i < len; i++) {
            MultipartFile file = files.get(i);
            String fileName = file.getOriginalFilename();
            //扩展名
            //abc.jpg
            String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
            String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
            log.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);

            File fileDir = new File(path);
            if(!fileDir.exists()){
                fileDir.setWritable(true);
                fileDir.mkdirs();
            }
            File targetFile = new File(path,uploadFileName);

            try {
                file.transferTo(targetFile);

                String targetFileName = targetFile.getName();
                String url = HttpPrefix+targetFileName;
                fileNames.add(targetFileName);
                urls.add(url);

                //文件已经上传成功了
                targetFiles.add(targetFile);

            } catch (IOException e) {
                log.error("上传文件异常",e);
                return ServerResponse.createByErrorMessage("上传失败");
            }
        }



        try {

            //上传到ftp服务器上
            boolean uploadResult = ftpUtil.uploadFile(remotePath,targetFiles);
            for(int i = 0, len = targetFiles.size(); i < len; i++) {
                targetFiles.get(i).delete();
            }

            if(!uploadResult) {
                return ServerResponse.createByErrorMessage("上传失败");
            }

            //已经上传到ftp服务器上
            Map<String,Object> dataMap = Maps.newHashMap();
            dataMap.put("fileNames",fileNames);
            dataMap.put("urls",urls);
            return ServerResponse.createBySuccess("上传成功",dataMap);

        } catch (IOException e) {
            log.error("上传文件异常",e);
            return ServerResponse.createByErrorMessage("上传失败");
        }

    }

}
