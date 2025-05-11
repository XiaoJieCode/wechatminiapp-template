package cn.wscsoft.backend.common.util;


import cn.wscsoft.backend.common.dto.BaseResponse;
import cn.wscsoft.backend.common.dto.BizException;
import cn.wscsoft.backend.common.dto.ErrorCode;

import java.util.function.Supplier;

/**
 * 抛异常工具类
 *
 * @author 观止
 */
public class ThrowUtils {
    public static void throwToastIf(boolean condition, String message) {
        if (condition) throw new BizException(message);
    }

    /**
     * 条件成立则抛异常
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    public static void throwIf(boolean condition, Throwable throwable) throws Throwable {
        if (condition) {
            throw throwable;
        }
    }

    /**
     * 条件成立则抛异常
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BizException(errorCode));
    }


    /**
     * 条件成立则抛异常
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BizException(errorCode, message));
    }

    /**
     * 条件成立则抛异常
     */
    public static void throwIfNull(Supplier<?> supplier, ErrorCode errorCode, String message) {
        throwIf(supplier == null, new BizException(errorCode, message));
        throwIf(supplier.get() == null, new BizException(errorCode, message));
    }

    /**
     * 传递的参数数值为null抛出异常
     */
    public static void throwIfParamNull(Object param) {
        if (param == null) {
            throw new BizException(ErrorCode.PARAMS_VOID);
        }
    }

    /**
     * 数值为null抛出异常
     */
    public static void throwIfParamNull(Object param, String message) {
        if (param == null) {
            throw new BizException(ErrorCode.PARAMS_VOID, message);
        }
    }

    /**
     * 数值为null抛出异常
     */
    public static void throwIfParamNull(Object param, ErrorCode errorCode) {
        if (param == null) {
            throw new BizException(errorCode);
        }
    }

    // 工具

    /**
     * 查询数据库对象数值为null抛出异常
     */
    public static void throwIfObjectNull(Object param) {
        if (param == null) {
            throw new BizException(ErrorCode.NOT_FOUND_ERROR);
        }
    }

    public static void throwIfObjectNull(Object param, String message) {
        if (param == null) {
            throw new BizException(ErrorCode.NOT_FOUND_ERROR);
        }
    }

    /**
     * 判断请求异常
     */
    public static <T> T throwIfFail(BaseResponse<T> res) {
        if (res.getCode() != 0) {
            throw new BizException(ErrorCode.SYSTEM_ERROR, res.getMessage());
        }
        return res.getData();
    }

    /**
     * 判断请求异常
     */
    public static <T> T throwIfFail(BaseResponse<T> res, String message) {
        if (res.getCode() != 0) {
            throw new BizException(ErrorCode.SYSTEM_ERROR, message);
        }
        return res.getData();
    }

}
