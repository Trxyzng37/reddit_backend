package com.trxyzng.trung.authentication.accesstoken;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AccessTokenValidationFilter extends OncePerRequestFilter {
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, NullPointerException {
        String accessToken = AccessTokenUtil.getAccessTokenFromHeader(request);
        //if no access_token in header ""
        if (accessToken.isBlank()) {
            System.out.println("No access_token in header");
            response.sendError(401, "No access_token in header");
            return;
        }
        Claims accessTokenClaim = AccessTokenUtil.parseAccessToken(accessToken);
        boolean is_valid = AccessTokenUtil.isValidAccessToken(accessTokenClaim);
        //if refresh_token is invalid
        if (!is_valid) {
            System.out.println("access_token is invalid filter");
            response.sendError(401, "access_token is invalid filter");
            return;
        }
        boolean is_expired = AccessTokenUtil.isAccessTokenExpired(accessTokenClaim);
        //if refresh_token is expired
        if (is_expired) {
            System.out.println("access_token is exprired filter");
            response.sendError(401, "access_token is exprired filter");
            return;
        }
        filterChain.doFilter(request, response);
    }
}