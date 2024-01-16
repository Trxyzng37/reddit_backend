package com.trxyzng.trung.authentication.refreshtoken;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RefreshTokenValidationFilter extends OncePerRequestFilter {
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, NullPointerException {
        String refreshToken = RefreshTokenUtil.getRefreshTokenFromCookie(request);
        //if no refresh_token in cookie
        if (refreshToken.isBlank()) {
            System.out.println("No refresh_token cookie in cookie");
            response.sendError(401, "No refresh_token cookie in cookie");
            return;
        }
        Claims refreshTokenClaim = RefreshTokenUtil.parseRefreshToken(refreshToken);
        boolean is_valid = RefreshTokenUtil.isValidRefreshToken(refreshTokenClaim);
        //if refresh_token is invalid
        if (!is_valid) {
            System.out.println("refresh_token is invalid filter");
            response.sendError(401, "refresh_token is invalid filter");
            return;
        }
        boolean is_expired = RefreshTokenUtil.isRefreshTokenExpired(refreshTokenClaim);
        //if refresh_token is expired
        if (is_expired) {
            System.out.println("refresh_token is exprired filter");
            response.sendError(401, "refresh_token is exprired filter");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
