package cn.gzticc.uploadserver.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件业务路径接口
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
