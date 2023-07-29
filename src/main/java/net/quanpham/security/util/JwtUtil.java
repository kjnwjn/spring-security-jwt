package net.quanpham.security.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
    private static final String SECRET_KEY = "oujoksaedr8useDSku5WERoaijrfaoswkjef$";
    private static final long EXPIRATION_TIME_MS = 3600000;
    private static final String subject = "security";

    public static String genJwt(Map<String, Object> claims) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME_MS);

        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        return jwtToken;
    }

    public static boolean validateJwtToken(String jwtToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(jwtToken);

            Claims claims = claimsJws.getBody();
            Date expiration = claims.getExpiration();
            Date now = new Date();

            return !expiration.before(now);
        } catch (Exception e) {
            return false;
        }
    }

    public static Claims decodeJwtToken(String jwtToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
