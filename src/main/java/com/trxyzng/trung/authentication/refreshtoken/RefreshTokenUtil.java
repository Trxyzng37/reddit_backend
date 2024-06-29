package com.trxyzng.trung.authentication.refreshtoken;

import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

public class RefreshTokenUtil {
    public static final long EXPIRE_DURATION = 10 * 60 * 1000; // minute second millisecond
    private static final String SECRET_KEY_STRING = "ThisIsMySecretKeyForCreatingRefreshToken";
    private  static final String ISSUER = "refresh_token";
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
            return new String();
        }
        catch (NullPointerException e) {
            System.out.println("Empty cookie");
            return new String();
        }
    }

    //get claims from refresh_token string
    public static Claims parseRefreshToken(String refresh_token) {
        try {
            return jwtParser.parseClaimsJws(refresh_token).getBody();
        }
        catch (ExpiredJwtException e) {
            System.out.println("refresh_token expired RefreshTokenUtil");
            return Jwts.claims(new HashMap<>());
        }
        catch (UnsupportedJwtException e) {
            System.out.println("refresh_token not supported RefreshTokenUtil");
            return Jwts.claims(new HashMap<>());
        }
        catch (MalformedJwtException e) {
            System.out.println("refresh_token malformed RefreshTokenUtil");
            return Jwts.claims(new HashMap<>());
        }
    }

    //return true if expired
    public static boolean isRefreshTokenExpired(Claims refreshTokenClaim) {
        if (refreshTokenClaim.isEmpty()) {
            System.out.println("Empty claims");
            return false;
        }
        System.out.println("Is refresh_token expiration time before current date: " + refreshTokenClaim.getExpiration().before(new Date()));
        return refreshTokenClaim.getExpiration().before(new Date());
    }

    public static boolean isValidRefreshToken(Claims refreshTokenClaim) {
        System.out.println("Claims: " + refreshTokenClaim);
        if (refreshTokenClaim.isEmpty()) {
            System.out.println("refresh_token is invalid RefreshTokenUtil");
            return false;
        }
        if (refreshTokenClaim.getIssuer().equals(ISSUER)) {
            System.out.println("refresh_token is valid RefreshTokenUtil");
            return true;
        }
        return false;
    }
}
