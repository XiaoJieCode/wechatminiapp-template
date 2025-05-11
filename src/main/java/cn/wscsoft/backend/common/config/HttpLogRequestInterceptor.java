package cn.wscsoft.backend.common.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 请求拦截器 - 打印请求的头信息和请求体
 */
@Slf4j
@Component
public class HttpLogRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        // 包装 request，确保支持多次读取请求体
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }

        // 读取请求头信息
        List<String> headerValues = new ArrayList<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            headerValues.add(header + "=" + request.getHeader(header));
        }

        // 获取基本信息
        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        boolean isGetRequest = "GET".equalsIgnoreCase(method);
        String body = "";

        // 仅在非 GET 请求时读取请求体
        if (!isGetRequest) {
            ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
            byte[] content = wrapper.getContentAsByteArray();
            if (content.length > 0) {
                body = new String(content, StandardCharsets.UTF_8);
            }
        }

        // 打印日志
        if (isGetRequest) {
            log.info("Http Headers: {} \n{} {} Query: {}",
                    String.join(", ", headerValues), method, url, queryString != null ? queryString : "");
        } else {
            log.info("Http Headers: {} \n{} {} Query: {}\nBody:\n{}\n",
                    String.join(", ", headerValues), method, url, queryString != null ? queryString : "", body.isEmpty() ? "<empty>" : body);
        }

        return true;
    }
}
