package cn.wscsoft.backend.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    public static final String SECRET_KEY_STR = "t0N3IxH3DpNcq49VMYsUCL_23s892UuYhWHIwspJK-6R_hH8sgyd5k_NZ4-zasGOMYceAAmxJEtAU4a764wUFQ==";
    public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STR.getBytes());

    // 测试生成token
    public static void main(String[] args) {
        String userId = "1668905385607352322";
        String token = generateToken(userId, 24 * 7 * 3600 * 1000);
        System.out.println("user id: " + userId);
        System.out.println("Bearer " + token);
        Claims claims = parseToken(token);
        System.out.println(claims.toString());
    }


    /**
     * 生成 JWT
     *
     * @return token
     */
    public static String generateToken(String userId, long expireTime) {
        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SECRET_KEY, Jwts.SIG.HS512)
                .compact();
    }

    /**
     * 解析 JWT
     */

    public static Claims parseToken(String token) {
        return (Claims) Jwts.parser().verifyWith(SECRET_KEY)
                .build()
                .parse(token)
                .getPayload();
    }

    /**
     * 从token中获取subject(userId)
     */
    public static long getUserId(String token) {
        return Long.parseLong(parseToken(token).getSubject());
    }


    /**
     * 校验 JWT 是否有效
     */

    public static boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
