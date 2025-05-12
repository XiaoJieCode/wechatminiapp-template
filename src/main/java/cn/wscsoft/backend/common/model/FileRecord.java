package cn.wscsoft.backend.common.model;

import cn.wscsoft.backend.common.enums.FileUploadBizEnum;
import cn.wscsoft.backend.common.enums.StorageType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("file_record")
public class FileRecord {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    private String fileName;
    private String fileType;
    private Long fileSize;
    /**
     * 存储类型
     */
    private StorageType storageType;
    private String storagePath;
    private String objectKey;
    /**
     * 上传者id
     */
    private Long uploaderId;
    /**
     * 业务类型
     */
    private FileUploadBizEnum biz;
    /**
     * 业务id
     */
    private String bizId;
    private Date createTime;
    private Boolean isDelete;
}