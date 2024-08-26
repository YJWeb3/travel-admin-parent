package com.zheng.travel.admin.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zheng.travel.admin.pojo.SysLoginUser;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtTokenUtils {
    // 携带token的请求头名字
    public final static String TOKEN_HEADER = "Authorization";
    //token的前缀
    public final static String TOKEN_PREFIX = "Bearer ";
    // 默认密钥
    public final static String DEFAULT_SECRET = "abcdefghijklmnoqprstuvwxyz";
    // 一分钟
    public final static long ONE_MINUTES = 60 * 1000;
    // App
    public final static long TOKEN_EXPIRATION = 30 * ONE_MINUTES;
    // 续期时间 是token是两倍
    public final static long REDIS_TOKEN_EXPIRATION = 2 * TOKEN_EXPIRATION;
    // 具体加密的算法
    private static final  Algorithm algorithm = Algorithm.HMAC256(DEFAULT_SECRET);


    /**
     * SysLoginUser 登录用户
     *
     * @return
     */
    public static String createToken(SysLoginUser sysLoginUser) {
        // 2：创建token
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("typ", "ZIP");
        return JWT.create()
                // 放进去的目的，方便你获取用户信息，避免查询,最后是去掉，
                // 后续可能会根据id去查询数据，实时的获取用户信息
                //.withClaim("token", FastJsonUtil.toJSON(sysLoginUser))
                .withHeader(headerClaims)
                // 放一份在主题里
                .withSubject(sysLoginUser.getId() + "")
                // 签发token作者
                .withIssuer(sysLoginUser.getNickname())
                //设置签发时间：iat
                .withIssuedAt(generateCurrentDate())
                //设置过期时间：exp，必须要大于签发时间
                .withExpiresAt(generateExpirationDate())
                //签名信息，采用secret作为私钥
                .sign(algorithm);
    }


    /**
     * 检验token是否正确
     *
     * @param token
     * @return
     */
    public static String parseToken(String token) {
        String userId;
        try {
            userId = getJWTFromToken(token).getSubject();
        } catch (Exception e) {
            userId = null;
        }
        return userId;

    }


    /**
     * 验证是否token过期
     *
     * @param token
     * @return
     */
    public static boolean isverfiy(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            // 如果token失效了，verify就会出现异常
            verifier.verify(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }



    /**
     * @param token
     * @return
     */
    public static DecodedJWT getJWTFromToken(String token) {
        DecodedJWT jwt;
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            jwt = verifier.verify(token);
            return jwt;
        } catch (Exception ex) {
            jwt = null;
            log.info("token is expired....");
        }
        return jwt;
    }


    /**
     * 从请求头中获取token
     *
     * @param request
     * @return
     */
    public static String getJwtToken(HttpServletRequest request) {
        // 如果cookie中没有，就去header里面获取
        String header = request.getHeader(TOKEN_HEADER);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            log.info("请求头不含JWT token， 调用下个过滤器");
            return null;
        }

        //去掉token prefix
        String token = header.split(" ")[1].trim();
        return token;
    }


    /**
     * 当前时间
     *
     * @return
     */
    private static Date generateCurrentDate() {
        return new Date();
    }

    /**
     * 过期时间
     *
     * @return
     */
    private static Date generateExpirationDate() {
        return new Date(DateTime.now().getMillis() + TOKEN_EXPIRATION);
    }


}
