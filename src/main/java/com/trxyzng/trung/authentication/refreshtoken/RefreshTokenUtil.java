package com.trxyzng.trung.authentication.refreshtoken;

import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

public class RefreshTokenUtil {
    private static final long EXPIRE_DURATION = 2 * 60 * 1000; // minute second millisecond
    private static final String SECRET_KEY_STRING = "ThisIsMySecretKeyForCreatingRefreshToken";
    private  static final String ISSUER = "refreshtoken";
    private static final byte[] keyBytes = SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8);
    private static final SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
    private static final JwtParser  jwtParser = Jwts.parser().verifyWith(secretKey).build();

    public static String generateRefreshToken(int id) {
        return Jwts.builder()
                .setSubject(String.format("%s", id))
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith( SignatureAlgorithm.HS256,secretKey)
                .compact();
    }

    //get refresh_token cookie if available
    public static String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        try {
            for (int i=0; i<cookies.length; i++) {
                if (cookies[i].getName().equals("refresh_token")) {
                    System.out.println("Found cookie refresh_token");
                    return cookies[i].getValue();
                }
                else {
                    System.out.println("Not the cookie");
                    System.out.println(cookies[i].getName());
                }
            }
            return "";
        }
        catch (NullPointerException e) {
            System.out.println("Empty cookie");
            return "";
        }
    }

    //get claims from refresh_token string
    private static Claims parseRefreshToken(String refresh_token) {
        try {
            return jwtParser.parseClaimsJws(refresh_token).getBody();
        }
        catch (ExpiredJwtException e) {
            System.out.println("refresh_token expired RefreshTokenUtil");
            return Jwts.claims(new HashMap<String, Object>());
        }
        catch (UnsupportedJwtException e) {
            System.out.println("refresh_token not supported RefreshTokenUtil");
            return Jwts.claims(new HashMap<String, Object>());
        }
        catch (MalformedJwtException e) {
            System.out.println("refresh_token malformed RefreshTokenUtil");
            return Jwts.claims(new HashMap<String, Object>());
        }
    }

    //check if the refresh_token claim is expired, return false if expired
    public static boolean isRefreshTokenExpired(String refresh_token) throws AuthenticationException {
        Claims claims = parseRefreshToken(refresh_token);
        if (claims.isEmpty())
            return false;
        System.out.println("Is claims expiration after current date: "+claims.getExpiration().after(new Date()));
        return !claims.getExpiration().after(new Date());
    }

    public static boolean isValidRefreshToken(String refresh_token) {
        Claims claims = parseRefreshToken(refresh_token);
        System.out.println("Claims: " + claims);
        if (claims.isEmpty()) {
            System.out.println("refresh_token is invalid RefreshTokenUtil");
            return false;
        }
        if (claims.getIssuer().equals(ISSUER)) {
            System.out.println("refresh_token is valid RefreshTokenUtil");
            return true;
        }
        return false;
    }
}
