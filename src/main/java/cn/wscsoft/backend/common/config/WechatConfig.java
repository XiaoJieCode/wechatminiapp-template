package cn.wscsoft.backend.common.config;


import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.security.PrivateKey;

@Slf4j
@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "wechat")
public class WechatConfig {
    /**
     * 小程序appid
     */
    private String appId;

    /**
     * 小程序app密钥
     */

    private String appSecret;
    /**
     * 商户号
     */
    private String merchantId;

    /**
     * 商户API私钥路径
     */
    private String privateKeyPath;

    /**
     * 商户证书序列号
     */
    private String merchantSerialNumber;

    /**
     * 商户APIV3密钥
     */
    private String apiV3Key;

    @PostConstruct
    public void init() {
        log.info("加载微信小程序配置, {}", this);
        if (appId == null || appSecret == null) {
//            throw new RuntimeException("微信小程序配置错误");
            log.error("未配置微信小程序appid");
        }
    }

//    /**
//     * 获取配置
//     *
//     * @return
//     * @throws IOException
//     */
//    public Config getConfig() throws IOException {
//        ClassPathResource resource = new ClassPathResource(privateKeyPath);
//        PrivateKey privateKey = PemUtil
//                .readPemPrivateKey(resource.getInputStream());
//        return new RSAAutoCertificateConfig.Builder()
//                .merchantId(merchantId)
//                // 使用 com.wechat.pay.java.core.util 中的函数从本地文件中加载商户私钥，商户私钥会用来生成请求的签名
//                .privateKey(privateKey)
//                .merchantSerialNumber(merchantSerialNumber)
//                .apiV3Key(apiV3Key)
//                .build();
//    }
}
