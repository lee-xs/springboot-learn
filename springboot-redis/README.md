# springboot-redis 基本操作

## 依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

## application.yaml

```yaml
spring:
  redis:
    # Redis数据库索引（默认为0）
    database: 0
    # Redis服务器地址
    host: 127.0.0.1
    # Redis服务器连接端口
    port: 6379
    # Redis服务器连接密码（默认为空）
    password:
    # 连接超时时间（毫秒）
    timeout: 6000
    jedis:
      pool:
        # 连接池最大连接数（使用负值表示没有限制）
        max-active: 8
        # 连接池中的最大空闲连接
        max-idle: 8
        # 连接池最大阻塞等待时间（使用负值表示没有限制）
        max-wait: -1
        # 连接池中的最小空闲连接
        min-idle: 0
```

## redis配置类
```java
/**
 * redis缓存配置类
 *
 * @Date 2019/10/15
 */
@Configuration
@EnableCaching
public class RedisConfig {

    /**
     * redisTemplate配置
     *
     * SpringBoot提供了对Redis的自动配置功能，在RedisAutoConfiguration中默认为我们配置了JedisConnectionFactory
     * （客户端连接）、RedisTemplate以及StringRedisTemplate（数据操作模板），其中StringRedisTemplate模板只针对
     * 键值对都是字符型的数据进行操作，本示例采用RedisTemplate作为数据操作模板，该模板默认采
     * 用JdkSerializationRedisSerializer的二进制数据序列化方式，为了方便演示本示例采
     * 用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值，使用StringRedisSerializer来序
     * 列化和反序列化redis的key值。
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //使用StringRedisSerializer来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(keySerializer());

        //值采用json序列化,默认使用JDK的序列化方式
        redisTemplate.setValueSerializer(valueSerializer());

        // 设置hash key 和value序列化模式
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());

        //调用afterPropertiesSet方法,此方法是应该是初始化参数和初始化工作
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private RedisSerializer<String> keySerializer(){
        return new StringRedisSerializer();
    }

    /**
     * 1、使用Jackson2JsonRedisSerializer需要指明序列化的类Class，可以使用Obejct.class
     *
     * 2、使用GenericJacksonRedisSerializer比Jackson2JsonRedisSerializer效率低，占用内存高。
     *
     * 3、GenericJacksonRedisSerializer反序列化带泛型的数组类会报转换异常，解决办法存储以JSON字符串存储。
     *
     * 4、GenericJacksonRedisSerializer和Jackson2JsonRedisSerializer都是以JSON格式去存储数据，都可以作为Redis的序列化方式。
     * @return
     */
    private RedisSerializer<Object> valueSerializer() {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();

        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        return jackson2JsonRedisSerializer;
    }

    /*private RedisSerializer<Object> valueSerializer(){
        return new GenericJackson2JsonRedisSerializer();
    }*/

}
```

## redis工具类

```java
/**
 * redis 工具类
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    /**====================common====================**/

    public void commonDelete(String... keys){
        if(keys != null && keys.length > 0){
            for(String key : keys){
                redisTemplate.delete(key);
            }
        }
    }

    /**====================String====================**/

    public void set(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**====================Hash====================**/

    public void hSet(String hashKey, String key, Object obj){
        redisTemplate.boundHashOps(hashKey).put(key, obj);
    }

    public Object hGet(String hashKey, String key){
        return redisTemplate.boundHashOps(hashKey).get(key);
    }

    public void hDelete(String hashKey, String... item){
        redisTemplate.boundHashOps(hashKey).delete(item);
    }

    public void hUpdate(String hashKey, String key, Object obj){
        hDelete(hashKey, key);
        hSet(hashKey, key, obj);
    }

    /**====================List====================**/

    public void lSet(String key, long index,Object obj){
        redisTemplate.opsForList().set(key, index, obj);
    }

    public Object lGet(String key, long index){
        return redisTemplate.opsForList().index(key, index);
    }


    /**====================Set====================**/

    public void sSet(String key, Object... objs){
        redisTemplate.opsForSet().add(key,objs);
    }

    public Object sGet(String key){
        return redisTemplate.opsForSet().members(key);
    }

}
```

## 简单测试
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void str(){
        String key = "name";
        String value = "李四";

        redisUtils.set(key, value);
        System.out.println(redisUtils.get(key));
    }

    @Test
    public void hMap() throws JSONException {
        JSONObject o1 = new JSONObject();
        o1.put("name", "李四");
        o1.put("age", "22");
        o1.put("sex", "男");
        o1.put("email", "lisi@qq.com");
        redisUtils.hSet("user","1" , o1);

        JSONObject o2 = new JSONObject();
        o2.put("name", "张三");
        o2.put("age", "33");
        o2.put("sex", "男");
        o2.put("email", "zhangsan@qq.com");
        redisUtils.hSet("user","2" , o2);

        System.out.println(redisUtils.hGet("user", "1"));
        System.out.println(redisUtils.hGet("user", "2"));
    }

}
```
#### 🎉 🎉 🎉 如有帮助，帮忙点个Star给予鼓励，谢谢