-- 用户表
CREATE TABLE user
(
    id          BIGINT AUTO_INCREMENT COMMENT 'id' PRIMARY KEY,
    email       VARCHAR(32)                           NULL COMMENT '个人邮箱(账号)',
    account     VARCHAR(20)                           NULL COMMENT '用户账户',
    password    VARCHAR(512)                          NULL COMMENT '密码',
    union_id    VARCHAR(64)                           NULL COMMENT '微信开放平台id',
    open_id     VARCHAR(64)                           NULL COMMENT '微信小程序openId',
    name        VARCHAR(256)                          NULL COMMENT '用户昵称',
    avatar      VARCHAR(1024)                         NULL COMMENT '用户头像',
    phone       VARCHAR(20)                           NULL COMMENT '用户手机号',
    profile     VARCHAR(512)                          NULL COMMENT '用户简介',
    role        VARCHAR(16) DEFAULT 'user'            NOT NULL COMMENT '用户角色：user/admin/ban',
    create_time DATETIME    DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    update_time DATETIME    DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_delete   TINYINT     DEFAULT 0                 NOT NULL COMMENT '是否删除'
) COMMENT '用户' COLLATE = utf8mb4_unicode_ci;
CREATE TABLE file_record
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_name    VARCHAR(255) NOT NULL COMMENT '原始文件名',
    file_type    VARCHAR(100) NOT NULL COMMENT '文件类型（image/png、video/mp4等）',
    file_size    BIGINT                DEFAULT 0 COMMENT '文件大小（字节）',
    storage_type VARCHAR(50)  NOT NULL COMMENT '存储方式（local/minio/oss）',
    storage_path VARCHAR(500) NOT NULL COMMENT '存储路径或URL',
    object_key   VARCHAR(255) NOT NULL COMMENT '文件标识（如minio的objectName）',
    uploader_id  BIGINT COMMENT '上传人ID',
    biz          VARCHAR(50)  NOT NULL COMMENT '业务模块标识（如user、product、post等）',
    biz_id       VARCHAR(100)          DEFAULT NULL COMMENT '业务关联ID（如帖子ID、用户ID）',
    create_time  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_delete    TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
    INDEX idx_biz (biz, biz_id),
    INDEX idx_uploader (uploader_id),
    INDEX idx_create_time (create_time)
) COMMENT = '上传文件记录表';
