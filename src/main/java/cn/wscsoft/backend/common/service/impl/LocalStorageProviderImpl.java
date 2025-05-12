package cn.wscsoft.backend.common.service.impl;

import cn.wscsoft.backend.common.config.UploadConfigProperties;
import cn.wscsoft.backend.common.enums.FileUploadBizEnum;
import cn.wscsoft.backend.common.enums.StorageType;
import cn.wscsoft.backend.common.service.FileRecordService;
import cn.wscsoft.backend.common.service.StorageProvider;
import cn.wscsoft.backend.common.util.UploadUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@ConditionalOnProperty(name = "upload.storage", havingValue = "local")
@Service
public class LocalStorageProviderImpl implements StorageProvider {

    @Resource
    private UploadConfigProperties config;
    @Resource
    private FileRecordService fileRecordService;

    @Override
    public String upload(MultipartFile file, FileUploadBizEnum bizType, String bizId, Long uploaderId) throws IOException {

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String filenameWithDir = bizType.getValue() + "/" + UploadUtil.getTodayDir() + "/" + filename;
        String fullPath = config.getLocal().getBasePath() + bizType.getValue() + "/" + UploadUtil.getTodayDir() + "/" + filename;

        File targetFile = new File(fullPath);
        boolean mkdirs = targetFile.getParentFile().mkdirs();
        file.transferTo(targetFile);
        String reachableUrl = config.getLocal().getBaseUrl() + filename;
        boolean recordSuccess = fileRecordService.recordUpload(
                filename, file.getContentType(), file.getSize()
                , reachableUrl, StorageType.LOCAL, filenameWithDir
                , bizType, bizId, uploaderId
        );
        if (!recordSuccess) {
            log.error("记录文件失败: {}", filename);
            boolean delete = targetFile.delete();
            if (!delete) {
                throw new IOException("记录上传记录失败");
            }
        }
        return reachableUrl;
    }
}
