package com.trxyzng.trung.refresh_token_server.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RefreshTokenAuthenticationFilter extends OncePerRequestFilter {
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, NullPointerException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            System.out.println("No cookie in header");
            response.getWriter().write("No cookie detected");
            response.sendError(400);
        } else {
            for (int i = 0; i < cookies.length; i++) {
                System.out.println("Name: "+cookies[i].getName());
                System.out.println("Value: "+cookies[i].getValue());
            }
        }
        filterChain.doFilter(request, response);
    }
}
