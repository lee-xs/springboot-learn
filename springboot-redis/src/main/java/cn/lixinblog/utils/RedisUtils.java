package cn.lixinblog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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