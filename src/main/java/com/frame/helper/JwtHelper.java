package com.frame.helper;

import com.frame.exception.MyException;
import com.frame.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

public class JwtHelper {
    private static long tokenExpiration = 24*60*60*1000;
    private static String tokenSignKey = "asdfghjkl123456";

    public static String createToken(int userId, String userName) {
        String token = Jwts.builder()
                .setSubject("SHIMMER-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .claim("userName", userName)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }
    public static int getUserId(String token) {
        if(StringUtils.isEmpty(token)) return -1;
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            int userId = (int)claims.get("userId");
            return userId;
        } catch (Exception e) {
            throw new MyException(ResultCodeEnum.LOGIN_AURH);
        }
    }
    public static String getUserName(String token) {
        if(StringUtils.isEmpty(token)) return "";
        Jws<Claims> claimsJws
                = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("userName");
    }
}