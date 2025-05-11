package cn.wscsoft.backend.common.config;

import cn.wscsoft.backend.common.dto.BaseResponse;
import cn.wscsoft.backend.common.dto.BizException;
import cn.wscsoft.backend.common.dto.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Component
@RestControllerAdvice
public class GlobalExceptionHandlerConfig  {

        @ExceptionHandler(BizException.class)
        public BaseResponse<?> businessExceptionHandler(BizException e) {
            log.error("BizException", e);
            ServletRequestAttributes req = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (req != null) {
                if (req.getResponse() != null) {
                    req.getResponse().setStatus(e.getHttpStatusCode());
                }
            }
            return new BaseResponse<>(e.getCode(), null, e.getMessage());
        }

        @ExceptionHandler(RuntimeException.class)
        public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
            log.error("RuntimeException", e);

            ServletRequestAttributes req = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (req != null) {
                if (req.getResponse() != null) {
                    req.getResponse().setStatus(ErrorCode.SYSTEM_ERROR.getHttpCode());
                }
            }
            return BaseResponse.fail(ErrorCode.SYSTEM_ERROR, "系统错误");
        }


}
