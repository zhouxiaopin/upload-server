package cn.gzticc.uploadserver.service;

import cn.gzticc.uploadserver.common.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件业务路径接口
 */
public interface IFileService {
    /**
     *
     * @param base64Strs
     * @param path
     * @return
     */
    ServerResponse imgUpload(String[] base64Strs, String path);

    ServerResponse videoUpload(MultipartFile[] files, String path);
}
