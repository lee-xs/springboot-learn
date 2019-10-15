package cn.lixinblog.controller;

import cn.lixinblog.service.QiniuService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UploadController {

    @Autowired
    private QiniuService qiniuService;

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @ResponseBody
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        return qiniuService.upload(file.getInputStream());
    }

    @ResponseBody
    @GetMapping("/delete/{key}")
    public Response delete(@PathVariable("key") String key) throws QiniuException {
        return qiniuService.delete(key);
    }

}
