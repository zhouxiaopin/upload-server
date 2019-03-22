package cn.gzticc.uploadserver.controller;

import cn.gzticc.uploadserver.common.ServerResponse;
import cn.gzticc.uploadserver.service.IFileService;
import cn.gzticc.uploadserver.utils.Base64Util;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 图片和视频上传的 Controller
 */
@RestController
@RequestMapping("upload")
public class UploadController {
    @Value("ftp.server.http.prefix")
    private String ftpServerHttpPrefix;
    @Autowired
    private IFileService fileService;

    /**
     * 图片上传
     * @param base64Str
     * @param request
     * @return
     */
    @PostMapping("imgUpload")
    public ServerResponse<Map<String,Object>> imgUpload(@RequestParam(value = "base64Str",required = false) String base64Str, HttpServletRequest request){
        MultipartFile file = Base64Util.base64ToMultipart(base64Str);
		String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = fileService.upload(file,path);
        String url = ftpServerHttpPrefix+targetFileName;
        Map<String,Object> fileMap = Maps.newHashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);

    }

    /**
     * 视频上传
     * @param request
     * @param file
     * @return
     */
    @PostMapping("videoUpload")
    public ServerResponse<Map<String,Object>> videoUpload(HttpServletRequest request,MultipartFile file){
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = fileService.upload(file,path);
        String url = ftpServerHttpPrefix+targetFileName;
        Map<String,Object> fileMap = Maps.newHashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);

    }
}
