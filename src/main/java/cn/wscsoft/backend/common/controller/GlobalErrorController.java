package cn.wscsoft.backend.common.controller;

import cn.wscsoft.backend.common.dto.BaseResponse;
import cn.wscsoft.backend.common.dto.ErrorCode;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GlobalErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping
    public BaseResponse<Void> handleError(HttpServletRequest request, HttpServletResponse response) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        response.setStatus(statusCode);
        return new BaseResponse<>(ErrorCode.SYSTEM_ERROR.getCode(), null, message);
    }
}
