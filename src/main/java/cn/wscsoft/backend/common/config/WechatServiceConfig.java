package cn.wscsoft.backend.common.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class WechatServiceConfig {


    /**
     * 配置微信小程序jdk api
     */
    @Bean
    public WxMaService wxMaService(WechatConfig wechatConfig) {
        WxMaService maService = new WxMaServiceImpl();
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(wechatConfig.getAppId());
        config.setSecret(wechatConfig.getAppSecret());
        maService.setWxMaConfig(config);
        return maService;
    }
//
//    /**
//     * 获取服务
//     *
//     * @return
//     * @throws IOException
//     */
//    @Bean
//    public JsapiServiceExtension jsapiServiceExtension(WechatConfig wechatConfig) throws IOException {
//        // 初始化商户配置
//        return new JsapiServiceExtension.Builder()
//                .config(wechatConfig.getConfig())
//                // 不填默认为RSA
//                .signType("RSA")
//                .build();
//    }

//    /**
//     * 获取通知回调解析
//     *
//     * @return
//     * @throws IOException
//     */
//    @Bean
//    public NotificationParser getNotificationParser(WechatConfig wechatConfig) throws IOException {
//        return new NotificationParser((NotificationConfig) wechatConfig.getConfig());
//    }
}
