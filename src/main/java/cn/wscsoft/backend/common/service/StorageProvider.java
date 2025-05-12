package cn.wscsoft.backend.common.service;

import cn.wscsoft.backend.common.dto.BizException;
import cn.wscsoft.backend.common.enums.FileUploadBizEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageProvider {
    String upload(MultipartFile file, FileUploadBizEnum bizType, String bizId, Long uploaderId) throws IOException;

    default Boolean delete(String objectKey) {
        throw new BizException("不支持的操作");
    }
}
