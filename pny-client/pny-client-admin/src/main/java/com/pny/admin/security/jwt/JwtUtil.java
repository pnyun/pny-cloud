package com.pny.admin.security.jwt;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.auth0.jwt.JWT;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pmyun
 * @since 2020/3/26 16:38
 */
@Slf4j
public class JwtUtil {

    /**
     * 密钥
     */
    private static final String SECRET = "my_secret";

    /**
     * 过期时间 单位为秒
     **/
    private static final long EXPIRATION = 1800L;

    /**
     * 生成用户token,设置token超时时间
     */
    public static String createToken(User user) {
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
            // 添加头部
            .withHeader(map)
            //可以将基本信息放到claims中
            .withClaim("userId", user.getUserId())
            .withClaim("userName", user.getUsername())
            .withClaim("realName", user.getRealname())
            //超时设置,设置过期的日期
            .withExpiresAt(expireDate)
            //签发时间
            .withIssuedAt(new Date())
            //SECRET加密
            .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    /**
     * 校验token并解析token
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("token解码异常");
            //解码异常则抛出异常
            return null;
        }
        return jwt.getClaims();
    }

}
