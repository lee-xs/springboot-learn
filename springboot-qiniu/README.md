# springboot上传图片到七牛云

- 说明：请先注册使用七牛云，在applicatiion.yaml配置个人密匙

## 依赖

```xml
    <!--引入七牛云-->
    <dependency>
        <groupId>com.qiniu</groupId>
        <artifactId>qiniu-java-sdk</artifactId>
        <version>[7.2.0, 7.2.99]</version>
    </dependency>
    
    <!--上传文件 -->
    <dependency>
        <groupId>commons-fileupload</groupId>
        <artifactId>commons-fileupload</artifactId>
        <version>1.3.1</version>
    </dependency>
    <dependency>
        <groupId>commons-io</groupId>
        <artifactId>commons-io</artifactId>
        <version>2.4</version>
    </dependency>
```

## application.yaml配置

```yaml
    qiniu主要配置信息
    qiniu:
      accessKey: 七牛云个人中心密匙 AccessKey
      secretKey: 七牛云个人中心密匙 SecretKey
      bucket: 存储空间名
      path: 访问的域名
```

## 七牛云Config

```java
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
```

## 属性

```java
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
```

## 代码实现

### service层

```java
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
```

### controller

```java
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
```

#### 🎉 🎉 🎉 如有帮助，帮忙点个Star给予鼓励，谢谢
