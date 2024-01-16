package com.trxyzng.trung.authentication.accesstoken;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Component
public class AccessTokenUtil {
    private static final long EXPIRE_DURATION = 1 * 30 * 1000; // minute second millisecond

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private  static final String ISSUER = "access_token";
    private static final String SECRET_KEY_STRING = "ThisIsMySecretKeyForCreatingAccessToken";
    private static final byte[] keyBytes = SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8);
    private static final SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
    private static final JwtParser  jwtParser = Jwts.parser().verifyWith(secretKey).build();
    public static String generateAccessToken(int id, String user) {
        return Jwts.builder()
                .setSubject(String.format("%s", id))
                .setIssuer(ISSUER)
                .claim("user", user)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith( SignatureAlgorithm.HS256,secretKey)
                .compact();
    }
    //return jwt token from the header
    public static String getAccessTokenFromHeader(HttpServletRequest request) {
        String accessToken = request.getHeader(TOKEN_HEADER);
        System.out.println("Get Authorization header: " + accessToken);
        if (accessToken.startsWith(TOKEN_PREFIX)) {
            return accessToken.substring(TOKEN_PREFIX.length());
        }
        return new String();
    }

    public static Claims parseAccessToken(String accessToken) {
        try {
            return jwtParser.parseClaimsJws(accessToken).getBody();
        }
        catch (ExpiredJwtException e) {
            System.out.println("access_token expired AccessTokenUtil");
            return Jwts.claims(new HashMap<String, Object>());
        }
        catch (UnsupportedJwtException e) {
            System.out.println("access_token not supported AccessTokenUtil");
            return Jwts.claims(new HashMap<String, Object>());
        }
        catch (MalformedJwtException e) {
            System.out.println("Access_token malformed AccessTokenUtil");
            return Jwts.claims(new HashMap<String, Object>());
        }
    }

    //return true if expired
    public static boolean isAccessTokenExpired(Claims AccessTokenClaim) {
        if (AccessTokenClaim.isEmpty()) {
            System.out.println("Empty claims");
            return false;
        }
        System.out.println("Is access_token expiration time before current date: " + AccessTokenClaim.getExpiration().before(new Date()));
        return AccessTokenClaim.getExpiration().before(new Date());
    }

    public static boolean isValidAccessToken(Claims AccessTokenClaim) {
        System.out.println("Claims: " + AccessTokenClaim);
        if (AccessTokenClaim.isEmpty()) {
            System.out.println("access_token is invalid AccessTokenUtil");
            return false;
        }
        if (AccessTokenClaim.getIssuer().equals(ISSUER)) {
            System.out.println("access_token is valid AccessTokenUtil");
            return true;
        }
        return false;
    }
}
