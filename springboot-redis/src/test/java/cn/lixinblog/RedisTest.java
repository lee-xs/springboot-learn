package cn.lixinblog;

import cn.lixinblog.utils.RedisUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
