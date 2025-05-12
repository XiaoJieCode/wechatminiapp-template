package cn.wscsoft.backend.common.service.impl;

import cn.wscsoft.backend.common.enums.FileUploadBizEnum;
import cn.wscsoft.backend.common.enums.StorageType;
import cn.wscsoft.backend.common.mapper.FileRecordMapper;
import cn.wscsoft.backend.common.model.FileRecord;
import cn.wscsoft.backend.common.service.FileRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class FileRecordServiceImpl extends ServiceImpl<FileRecordMapper, FileRecord> implements FileRecordService {
    /**
     * 记录上传的文件
     *
     * @param storageUrl  存储地址
     * @param storageType 存储类型
     * @param objectKey   对象唯一名称
     * @param bizType     业务类型
     * @param bizId       业务辅助id
     * @param uploaderId  上传者id
     */
    @Override
    public boolean recordUpload(String fileName, String fileType, long fileSize,
                                String storageUrl, StorageType storageType,
                                String objectKey, FileUploadBizEnum bizType,
                                String bizId, Long uploaderId) {
        FileRecord record = new FileRecord();
        record.setFileName(fileName);
        record.setFileType(fileType);
        record.setFileSize(fileSize);
        record.setStorageType(storageType);
        record.setStoragePath(storageUrl);
        record.setObjectKey(objectKey);
        record.setUploaderId(uploaderId);
        record.setBiz(bizType);
        record.setBizId(bizId);
        return save(record);
    }
}
