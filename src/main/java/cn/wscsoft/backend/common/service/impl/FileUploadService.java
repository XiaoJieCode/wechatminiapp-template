package cn.wscsoft.backend.common.service.impl;

import cn.wscsoft.backend.common.enums.FileUploadBizEnum;
import cn.wscsoft.backend.common.service.StorageProvider;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class FileUploadService {

    @Resource
    private StorageProvider storageProvider;

    /**
     * 上传文件
     *
     * @param file       文件对象
     * @param biz        业务类型
     * @param bizId      业务辅助id
     * @param uploaderId 上传人id
     * @return 可访问的url
     * @throws IOException 操作出错时的异常
     */
    public String uploadFile(MultipartFile file, FileUploadBizEnum biz, String bizId, long uploaderId) throws IOException {
        return storageProvider.upload(file, biz, bizId, uploaderId);
    }


}
