package cn.wscsoft.backend.common.service;

import cn.wscsoft.backend.common.enums.FileUploadBizEnum;
import cn.wscsoft.backend.common.enums.StorageType;
import cn.wscsoft.backend.common.model.FileRecord;
import com.baomidou.mybatisplus.extension.service.IService;

public interface FileRecordService extends IService<FileRecord> {
    boolean recordUpload(String fileName, String fileType, long fileSize,
                         String storageUrl, StorageType storageType,
                         String objectKey, FileUploadBizEnum bizType,
                         String bizId, Long uploaderId);
}
