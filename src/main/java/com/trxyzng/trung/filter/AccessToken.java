package com.trxyzng.trung.filter;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Component
public class AccessToken {
    private static final long EXPIRE_DURATION = 5 * 60 * 1000; // minute second millisecond

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";
    private final JwtParser  jwtParser;
    private String SECRET_KEY_STRING = "ThisIsMySecretKeyForCreatingAccessToken";
    byte[] keyBytes = SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8);
    SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
    public String generateAccessToken(int id, String user) {
        return Jwts.builder()
                .setSubject(String.format("%s", id))
                .setIssuer("accesstoken")
                .claim("user", user)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith( SignatureAlgorithm.HS256,secretKey)
                .compact();
    }
    private AccessToken(){
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }
    //return jwt token from the header
    public String getAccessTokenInHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateExpiration(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }
    //parse the jwt token and return the original payload (claims)
    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }
    //get claims from payload of jwt token
    public Claims getBodyClaims(HttpServletRequest req) {
        try {
            String token = getAccessTokenInHeader(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }
}
