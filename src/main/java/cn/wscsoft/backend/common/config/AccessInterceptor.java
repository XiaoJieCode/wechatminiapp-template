package cn.wscsoft.backend.common.config;


import cn.wscsoft.backend.common.annos.AuthedApi;
import cn.wscsoft.backend.common.dto.BaseResponse;
import cn.wscsoft.backend.common.dto.BizException;
import cn.wscsoft.backend.common.dto.ErrorCode;
import cn.wscsoft.backend.common.service.impl.AuthService;
import cn.wscsoft.backend.common.util.Context;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

import static cn.wscsoft.backend.common.util.JsonUtil.GSON;


@Slf4j
@Configuration
@Order
public class AccessInterceptor implements HandlerInterceptor {

    @Resource
    private AuthService authService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            log.info("[Api Auth] Call Method: {}", handler);
            AuthedApi methodAnno = handlerMethod.getMethodAnnotation(AuthedApi.class);
            AuthedApi typeAnno = AnnotatedElementUtils.findMergedAnnotation(handlerMethod.getBeanType(), AuthedApi.class);

            if (methodAnno == null && typeAnno == null) {

                return true;
            }

            AuthedApi anno = methodAnno != null ? methodAnno : typeAnno;

            if (anno.login()) {

                try {
                    Long userId = Context.getUserId();
                    authService.checkUserPermission(userId, anno.rolesStr(), anno.roles());
                } catch (BizException e) {
                    String json = GSON.toJson(new BaseResponse<>(e.getCode(), null, e.getMessage()));
                    log.warn("[Api Auth]: Fail Response{message={}, httpCode={}, response={}}", e.getMessage(), e.getHttpStatusCode(), json);
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(json);
                    return false;

                }
            }
        }
        return true;
    }
}

