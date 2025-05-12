package cn.wscsoft.backend.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data

@ConfigurationProperties(prefix = "upload")
@Configuration
public class UploadConfigProperties {
    /**
     * storage类型
     * 可选: minio local
     *
     */
    private String storage;
    private Local local = new Local();
    private Minio minio = new Minio();

    @Data
    public static class Local {
        private String basePath;
        private String baseUrl;
    }

    @Data
    public static class Minio {
        private String endpoint;
        private String accessKey;
        private String secretKey;
        private String bucket;
    }
}
