package cn.wscsoft.backend.common.controller;

import cn.wscsoft.backend.common.dto.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(("/"))
@RestController
public class PingController {
    @RequestMapping("/ping")
    public BaseResponse<String> ping() {
        return BaseResponse.success("pong");
    }

}
