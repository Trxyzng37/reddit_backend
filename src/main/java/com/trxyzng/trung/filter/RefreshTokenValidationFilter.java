package com.trxyzng.trung.filter;

import com.trxyzng.trung.utility.RefreshTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RefreshTokenValidationFilter extends OncePerRequestFilter {
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, NullPointerException {
        String refresh_token = RefreshTokenUtils.getRefreshTokenFromCookie(request);
        //if no refresh_token in cookie
        if (refresh_token.isBlank()) {
            System.out.println("No refresh_token cookie in cookie");
            response.sendError(401, "No refresh_token cookie in header");
            return;
        }
        boolean is_unexpired = RefreshTokenUtils.isRefreshTokenExpired(refresh_token);
        //if refresh_token is unexpired
        if (!is_unexpired) {
            System.out.println("refresh_token is exprired");
            response.sendError(401, "refresh_token is exprired");
        }
        //if refresh_token is valid
        boolean is_valid = RefreshTokenUtils.isValidRefreshToken(refresh_token);
        if (!is_valid) {
            System.out.println("refresh_token is invalid");
            response.sendError(401, "refresh_token is invalid");
        }
        filterChain.doFilter(request, response);
    }
}
