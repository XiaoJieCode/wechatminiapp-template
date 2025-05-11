package cn.wscsoft.backend.common.service;


import cn.wscsoft.backend.common.dto.UserSessionInfoRecord;
import org.springframework.stereotype.Service;

@Service
public interface WechatMiniAppService {
    /**
     * 获取微信小程序登录session信息
     *
     * @param code 小程序端调用wx.login()方法获取的code
     * @return UserSessionInfoRecord
     */
    UserSessionInfoRecord getWechatUserSessionInfo(String code) ;
}
