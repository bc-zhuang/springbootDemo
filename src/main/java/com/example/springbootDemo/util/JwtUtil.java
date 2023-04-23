package com.example.springbootDemo.util;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String createJWT(String id, String subject, long ttlMillis, Map<String, Object> map) throws Exception {
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject) // 发行者
                .setIssuedAt(new Date()) // 发行时间
                .signWith(SignatureAlgorithm.HS256, secretKey) // 签名类型 与 密钥
                .compressWith(CompressionCodecs.DEFLATE);// 对载荷进行压缩
        if (!CollectionUtils.isEmpty(map)) {
            builder.setClaims(map);
        }
        if (ttlMillis > 0) {
            builder.setExpiration(new Date(System.currentTimeMillis() + ttlMillis));
        }
        return builder.compact();
    }


    public Claims parseJWT(String jwtString) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(jwtString)
                .getBody();
    }
}

