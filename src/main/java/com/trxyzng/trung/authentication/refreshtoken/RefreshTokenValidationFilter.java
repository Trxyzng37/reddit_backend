package com.trxyzng.trung.authentication.refreshtoken;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Configuration
public class RefreshTokenValidationFilter extends OncePerRequestFilter {
//    @Autowired
//    RefreshTokenRepo refreshTokenRepo;
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, NullPointerException {
        try {
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
                System.out.println("refresh_token is invalid");
                response.sendError(401, "refresh_token is invalid");
                return;
            }
            boolean is_expired = RefreshTokenUtil.isRefreshTokenExpired(refreshTokenClaim);
            //if refresh_token is expired
            if (is_expired) {
                System.out.println("refresh_token is exprired");
                response.sendError(401, "refresh_token is exprired");
                return;
            }
//            boolean is_exist = refreshTokenRepo.isRefreshTokenExistInDb(refreshToken) == 1 ? true : false;
////            if refresh_token not exist in db
//            if(!is_exist) {
//                System.out.println("refresh_token is not in db");
//                response.sendError(401, "refresh_token is not in db");
//                return;
//            }
            filterChain.doFilter(request, response);
        }
        catch (Exception e) {
            System.out.println("error get refresh_token");
            response.sendError(401, "refresh_token error");
            return;
        }
    }
}
