package cn.lixinblog;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootShiroTest {

    @Test
    public void password(){
        String hashAlgorithmName = "MD5";//加密方式
        int hashIterations = 10;//加密次数
        String password = "fugui";//密码

        String uuid = UUID.randomUUID().toString().replace("-", "");//UUID
        ByteSource salt = ByteSource.Util.bytes(uuid);//盐值
        SimpleHash result = new SimpleHash(hashAlgorithmName, password, salt, hashIterations);

        System.err.println("uuid => " + uuid);
        System.err.println("密码 => " + result.toString());
    }

}
