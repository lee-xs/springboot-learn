package cn.lixinblog.config;

import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(QiniuProperties.class)
public class QiniuConfig {

    @Autowired
    private QiniuProperties qiniuProperties;

    /**
     * 构造一个带指定Region对象的配置类
     *
     * 华东	    Region.region0(), Region.huadong()
     * 华北	    Region.region1(), Region.huabei()
     * 华南	    Region.region2(), Region.huanan()
     * 北美	    Region.regionNa0(), Region.beimei()
     * 东南亚	Region.regionAs0(), Region.xinjiapo()
     *
     * 若不指定 Region 或 Region.autoRegion() ，则会使用 自动判断 区域，使用相应域名处理
     * 如果可以明确 区域 的话，最好指定固定区域，这样可以少一步网络请求，少一步出错的可能
     */
    @Bean
    public com.qiniu.storage.Configuration config(){
        return new com.qiniu.storage.Configuration(Region.huabei());
    }

    /**
     * 构建七牛上传工具实例
     */
    @Bean
    public UploadManager uploadManager(){
        return new UploadManager(config());
    }

    @Bean
    public Auth auth(){
        return Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
    }

    /**
     * 构建七牛空间管理实例
     */
    @Bean
    public BucketManager bucketManager(){
        return new BucketManager(auth(), config());
    }

}
