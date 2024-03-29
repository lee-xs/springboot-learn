package cn.aleestar.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
