package cn.wscsoft.backend.common.dto;


import lombok.Getter;

/**
 * 自定义异常类
 *
 * @author xwj
 */
@Getter
public class BizParamException extends BizException {
    public BizParamException(String message) {
        super(message, ErrorCode.PARAMS_ERROR.getHttpCode(), ErrorCode.PARAMS_ERROR.getCode());
    }
}
