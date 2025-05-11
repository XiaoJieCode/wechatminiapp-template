package cn.wscsoft.backend.common.dto;


import lombok.Getter;

/**
 * 自定义异常类
 *
 * @author xwj
 */
@Getter
public class BizException extends RuntimeException {
    private int httpStatusCode = 500;
    /**
     * 错误码
     */
    private int code = ErrorCode.SYSTEM_ERROR.getCode();

    public BizException(String message, int httpStatusCode, int code) {
        super(message);
        this.httpStatusCode = httpStatusCode;
        this.code = code;
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String message) {
        super(message);
    }


    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BizException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public BizException() {
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

}
