package cn.wscsoft.backend.common.service.impl;

import cn.wscsoft.backend.common.config.UploadConfigProperties;
import cn.wscsoft.backend.common.enums.FileUploadBizEnum;
import cn.wscsoft.backend.common.enums.StorageType;
import cn.wscsoft.backend.common.service.FileRecordService;
import cn.wscsoft.backend.common.service.StorageProvider;
import cn.wscsoft.backend.common.util.UploadUtil;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
@ConditionalOnProperty(name = "upload.storage", havingValue = "minio")
public class MinioStorageProviderImpl implements StorageProvider {
    @Resource
    private FileRecordService fileRecordService;
    private final MinioClient minioClient;
    private final UploadConfigProperties.Minio config;

    @Autowired
    public MinioStorageProviderImpl(UploadConfigProperties properties) {
        this.config = properties.getMinio();
        this.minioClient = MinioClient.builder()
                .endpoint(config.getEndpoint())
                .credentials(config.getAccessKey(), config.getSecretKey())
                .build();
    }

    @Override
    public String upload(MultipartFile file, FileUploadBizEnum bizType, String bizId, Long uploaderId) throws IOException {
        String filename = bizType.getValue() + "/" +
                UploadUtil.getTodayDir() + "/" +
                UUID.randomUUID() + "_" + file.getOriginalFilename();

        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(config.getBucket())
                    .object(filename)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            String reachableUrl = config.getEndpoint() + "/"
                    + config.getBucket() + "/"
                    + filename;

            boolean recordSuccess = fileRecordService.recordUpload(
                    file.getOriginalFilename(), file.getContentType(),file.getSize()
                    ,reachableUrl, StorageType.MINIO, filename,
                    bizType, bizId, uploaderId);
            if (!recordSuccess) {
                minioClient.removeObject(RemoveObjectArgs.builder()
                        .bucket(config.getBucket())
                        .object(filename)
                        .build());
                log.error("记录上传记录失败: {}", filename);
                throw new IOException("记录上传记录失败");
            }
            return reachableUrl;
        } catch (Exception e) {
            log.error("上传到 MinIO 失败", e);
            throw new IOException("上传到 MinIO 失败", e);
        }
    }
}
