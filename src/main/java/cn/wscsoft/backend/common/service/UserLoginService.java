package cn.wscsoft.backend.common.service;


import cn.wscsoft.backend.common.model.User;
import cn.wscsoft.backend.common.vo.LoginTokenVO;
import cn.wscsoft.backend.common.vo.LoginUserVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 用户登录服务接口
 */
public interface UserLoginService {
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 通过账号密码登录
     */
    LoginTokenVO userLoginByUserAccount(String userAccount, String userPassword);

    /**
     * 通过小程序登录
     *
     * @param code 小程序wx.login响应的code
     */
    LoginUserVO userLoginByMiniOpen( String code);

}
