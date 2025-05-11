package cn.wscsoft.backend.common.config;


import cn.wscsoft.backend.common.constant.ContextConstant;
import cn.wscsoft.backend.common.util.Context;
import cn.wscsoft.backend.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@Order(Integer.MIN_VALUE)
public class JwtGatewayFilter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {

            String token = request.getHeaders(HttpHeaders.AUTHORIZATION).nextElement();

            if (token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
                if (JwtUtil.validateToken(token)) {
                    Claims claims = JwtUtil.parseToken(token);
                    // 将用户ID添加到请求头中
                    Context.getMap().put(ContextConstant.USER_ID_KEY, Long.parseLong(claims.getSubject()));
                }
            }
            return true;
        } catch (Throwable e) {
            log.debug("err get user id", e);
            return true;
        }

    }


}