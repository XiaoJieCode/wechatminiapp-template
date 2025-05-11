package cn.wscsoft.backend.common.dto;

import lombok.Getter;

/**
 * 自定义错误码
 *
 * @author 观止
 */
@Getter
public enum ErrorCode {

    SUCCESS(0, "ok", 200),
    SYSTEM_ERROR(50000, "系统内部异常", 500),
    PARAMS_ERROR(50001, "请求参数错误", 200),
    PARAMS_VOID(50002, "请求参数为空", 500),

    RETRY_ERROR(50004, "请重试", 200),

    NOT_LOGIN_ERROR(40100, "未登录", 401),

    NO_AUTH_ERROR(40301, "无权限", 403),
    BAN_ERROR(40302, "账号已被禁用", 403),
    FORBIDDEN_ERROR(40303, "禁止访问", 403),
    NOT_FOUND_ERROR(40400, "请求数据不存在", 404);


    /**
     * 状态码
     */
    private final int code;
    private final int httpCode;
    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message, int httpCode) {
        this.code = code;
        this.message = message;
        this.httpCode = httpCode;
    }

}
