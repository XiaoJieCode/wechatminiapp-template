package cn.wscsoft.backend.common.config;

import cn.wscsoft.backend.common.constant.ContextConstant;
import cn.wscsoft.backend.common.util.Context;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.http.HttpHeaders;

/**
 * OpenFeign请求上下文数据注入
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class ContextRequestInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) throws Exception {
        Context.clear();
    }
}
