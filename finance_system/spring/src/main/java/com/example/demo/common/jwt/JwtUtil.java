package com.example.demo.common.jwt;

import com.example.demo.entity.User;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * @Description JwtUtil工具类（生成token,解析token,校验token）
 * @Author ame
 * @Date 2021/7/20 10:49
 * @Version 2.0
 */
public class JwtUtil {

    /**
     * 过期时间15分钟
     */
    private static final long EXPIRE_TIME = 60 * 60 * 1000 * 4 * 5;
    /**
     * token私钥
     */
    private static final String TOKEN_SECRET="DD5654D654DSD5S1D65S4D65S1D";


    /**
     * 生成签名15分钟后过期
     */
    public static String createToken(User user){
        JwtBuilder jwtBuilder = Jwts.builder();

        String token = jwtBuilder
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                .claim("username",user.getUserName())
                .claim("userid",user.getUsrId())
                .setSubject(user.getUserName())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_TIME))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256,TOKEN_SECRET)
                .compact();
        return token;
    }

    public static boolean parseJwtToken(String token) {
        try{
            Jws<Claims> jws = Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public static User getUserInfo(HttpServletRequest request) {
        User user = new User();
        try {
            String token = request.getHeader("token");
            Claims claims = Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody();
            user.setUsrId((Integer) claims.get("userid"));
            user.setUserName((String) claims.get("username"));
            return user;
        }
        catch (Exception e){
            return user;
        }
    }
}
