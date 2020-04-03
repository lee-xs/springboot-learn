package cn.aleestar.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.InputStream;


public interface QiniuService {

    String upload(InputStream file) throws QiniuException;

    Response delete(String key) throws QiniuException;

}
