package cn.wscsoft.backend.common.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @param <T>
 * @author xwj
 */
@Data
@NoArgsConstructor
public class BaseResponse<T> implements Serializable {
    // 响应码
    private int code;
    // 响应数据
    private T data;
    // 信息
    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static <T> BaseResponse<T> fail(ErrorCode errorCode) {
        return new BaseResponse<T>(errorCode.getCode(), null, errorCode.getMessage());
    }

    public static <T> BaseResponse<T> fail(ErrorCode errorCode, String message) {
        return new BaseResponse<T>(errorCode.getCode(), null, message);
    }

    public static BaseResponse<?> fail(String message) {
        return BaseResponse.fail(ErrorCode.PARAMS_ERROR, message);
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<T>(ErrorCode.SUCCESS.getCode(), data, ErrorCode.SUCCESS.getMessage());
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), null, ErrorCode.SUCCESS.getMessage());
    }


}
