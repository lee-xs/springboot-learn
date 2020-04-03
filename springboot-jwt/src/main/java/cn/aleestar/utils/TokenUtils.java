package cn.aleestar.utils;


import cn.aleestar.dto.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;

/**
 * 生成Token的工具类
 */
@Slf4j
public class TokenUtils {

    /**
     * 签名密匙（唯一密匙，可以用密码作为密匙）
     */
    public static final String SECRET = "token-secret";

    /**
     * Token有效时长 单位 ms
     */
    public static final long MAXAGE = 1000 * 60 * 5;


    /**
     * 生成Token
     * @param user 载体信息
     * @param maxAge 有效时长
     * @return
     */
    public static String createToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        /**
         * issuer    该JWT的签发者，是否使用是可选的
         * subject   该JWT所面向的用户，是否使用是可选的；
         */
        String token = JWT.create()
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + MAXAGE))
                .sign(algorithm);
        log.warn("token -> " + token);
        return token;
    }

    public static String createTokenBase64(User user){
        try{
            return Base64.getEncoder().encodeToString(createToken(user).getBytes("utf-8"));
        }catch (UnsupportedEncodingException e){
            log.error("生成加密Token出现异常", e);
            return null;
        }
    }

    /**
     * 解析验证Token
     * @param token 加密后的token字符串
     * @return
     */
    public static DecodedJWT verifyToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT jwt = jwtVerifier.verify(token);
            return jwt;
        } catch (JWTVerificationException e) {
            log.error("Token验证不通过", e);
            throw new RuntimeException("Token验证不通过");
        }
    }

    public static DecodedJWT verifyTokenBase64(String token){
        try{
            String str = new String(Base64.getDecoder().decode(token), "utf-8");
            return verifyToken(str);
        }catch (UnsupportedEncodingException e){
            log.error("解密Token出现异常", e);
            throw new RuntimeException("解密Token出现异常");
        }
    }


}
