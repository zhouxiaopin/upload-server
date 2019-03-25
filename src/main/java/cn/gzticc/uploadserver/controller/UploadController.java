package cn.gzticc.uploadserver.controller;

import cn.gzticc.uploadserver.common.ServerResponse;
import cn.gzticc.uploadserver.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 图片和视频上传的 Controller
 */
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private IFileService fileService;

    /**
     * 图片上传
     * @param base64Strs
     * @param request
     * @return
     */
    @PostMapping("imgUpload")
    public ServerResponse<Map<String,Object>> imgUpload(@RequestParam("base64Strs") String[] base64Strs, HttpServletRequest request){
		String path = request.getSession().getServletContext().getRealPath("upload");
        return fileService.imgUpload(base64Strs,path);

    }

    /**
     * 视频上传
     * @param files
     * @param request
     * @return
     */
    @PostMapping("videoUpload")
    public ServerResponse<Map<String,Object>> videoUpload(@RequestParam("files") MultipartFile[] files,HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("upload");
        return fileService.videoUpload(files,path);

    }
}
