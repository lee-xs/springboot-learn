package cn.lixinblog.service.impl;

import cn.lixinblog.config.QiniuProperties;
import cn.lixinblog.service.QiniuService;
import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Slf4j
@Service("qiniuService")
public class QiniuServiceImpl implements QiniuService {

    @Autowired
    private Auth auth;

    @Autowired
    private QiniuProperties qiniuProperties;

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private BucketManager bucketManager;

    /**
     * 以流的形式上传文件
     * @param file 文件流
     * @return
     */
    @Override
    public String upload(InputStream file) throws QiniuException {
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        Response response = uploadManager.put(file, null, uploadToken(), null, null);
        int retry = 0;
        while(response.needRetry() && retry < 5){
            response = uploadManager.put(file, null, uploadToken(), null, null);
            retry++;
        }
        //解析结果
        DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
        String returnPath = qiniuProperties.getPath() + "/" + putRet.key;
        log.warn("上传的文件路径：" + returnPath);
        return returnPath;
    }

    /**
     * 删除七牛云上的文件
     * @param key
     * @return
     * @throws QiniuException
     */
    @Override
    public Response delete(String key) throws QiniuException {
        Response response = bucketManager.delete(qiniuProperties.getBucket(), key);
        int retry = 0;
        while(response.needRetry() && retry < 5){
            response = bucketManager.delete(qiniuProperties.getBucket(), key);
            retry++;
        }
        return response;
    }

    /**
     * 获取上传凭证
     * @return
     */
    private String uploadToken(){
        return auth.uploadToken(qiniuProperties.getBucket(), null, 3600, null);
    }

}
