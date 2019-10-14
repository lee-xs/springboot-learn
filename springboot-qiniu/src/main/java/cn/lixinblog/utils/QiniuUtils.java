package cn.lixinblog.utils;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;

@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiniuUtils {

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

    /**
     * @param file 文件流
     * @param fileName 默认不指定key的情况下，以文件内容的hash值作为文件名
     * @return
     */
    public String upload(FileInputStream file,String fileName){
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
        Configuration cfg = new Configuration(Region.huabei());
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传
        try{
            Auth auth = Auth.create(accessKey, secretKey);
            String uploadToken = auth.uploadToken(bucket);
            try{
                Response response = uploadManager.put(file, fileName, uploadToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
                String resPath = path + "/" + putRet.key;
                return resPath;
            }catch (QiniuException e){
                Response r = e.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }

        return "";
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
