package cn.wscsoft.backend.common.controller;

import cn.wscsoft.backend.common.annos.AuthedApi;
import cn.wscsoft.backend.common.dto.BaseResponse;
import cn.wscsoft.backend.common.service.UserLoginService;
import cn.wscsoft.backend.common.vo.LoginUserVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {
    @Resource
    private UserLoginService userLoginService;
    /**
     * 用户
     *
     * @param code 小程序登录code
     * @return 登录用户信息
     */
    @GetMapping("/login/wx_mini")
    public BaseResponse<LoginUserVO> loginMiniApp(@RequestParam("code") String code) {
        return BaseResponse.success(userLoginService.userLoginByMiniOpen(code));
    }
}
