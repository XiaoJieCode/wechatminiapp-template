package cn.wscsoft.backend.common.config;

import cn.wscsoft.backend.common.dto.BaseResponse;
import cn.wscsoft.backend.common.dto.BizException;
import cn.wscsoft.backend.common.dto.ErrorCode;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Configuration
public class CommonWebMvcConfigurer implements WebMvcConfigurer {
    @Resource
    private HttpLogRequestInterceptor httpLogRequestInterceptor;
    @Resource
    private ContextRequestInterceptor contextRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // http 日志打印
        registry.addInterceptor(httpLogRequestInterceptor)
                .addPathPatterns("/**");  // 拦截所有请求
        // context 功能
        registry.addInterceptor(contextRequestInterceptor)
                .addPathPatterns("/api");
    }

}


