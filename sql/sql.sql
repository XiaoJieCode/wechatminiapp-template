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
