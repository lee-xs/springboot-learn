package cn.lixinblog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiniuProperties {

    /**
     * AccessKey
     */
    private String accessKey;

    /**
     * SecretKey
     */
    private String secretKey;

    /**
     * 存储空间名称
     */
    private String bucket;

    /**
     * 访问域名
     */
    private String path;

}
