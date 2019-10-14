package cn.lixinblog.controller;

import cn.lixinblog.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class UploadController {

    @Autowired
    private QiniuUtils qiniuUtils;

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @ResponseBody
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile[] file){
        if (file != null && file.length > 0) {
            for (int i = 0; i < file.length; i++) {
                try {
                    FileInputStream fileInputStream = (FileInputStream) file[i].getInputStream();
                    String url = qiniuUtils.upload(fileInputStream, null);
                    //输出url上传后的,可以复制url到浏览器访问
                    System.out.println("url=" + url);
                    try {
                        //延迟两秒让七牛云缓一下
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }

}
