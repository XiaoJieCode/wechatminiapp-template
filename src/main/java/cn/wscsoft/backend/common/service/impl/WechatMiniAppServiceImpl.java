package cn.wscsoft.backend.common.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.wscsoft.backend.common.dto.BizException;
import cn.wscsoft.backend.common.dto.UserSessionInfoRecord;
import cn.wscsoft.backend.common.service.WechatMiniAppService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WechatMiniAppServiceImpl implements WechatMiniAppService {
    @Resource
    private WxMaService wxMaService;

    /**
     * 获取微信小程序登录session信息
     *
     * @param code 小程序端调用wx.login()方法获取的code
     * @return UserSessionInfoRecord
     */
    @Override
    public UserSessionInfoRecord getWechatUserSessionInfo(String code) {
        WxMaUserService userService = wxMaService.getUserService();
        WxMaJscode2SessionResult sessionInfo = null;
        try {
            sessionInfo = userService.getSessionInfo(code);
        } catch (WxErrorException e) {
            log.error("微信服务调用异常", e);
            throw new BizException("微信服务异常！");
        }
        return new UserSessionInfoRecord(
                sessionInfo.getSessionKey(),
                sessionInfo.getOpenid(),
                sessionInfo.getUnionid()
        );
    }


}
