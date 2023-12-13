//package com.trxyzng.trung.filter;
//
//import io.jsonwebtoken.Claims;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
////@Component
//public class TestFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        try {
//            CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
//                    new CachedBodyHttpServletRequest(request);
//            String body = readBody.readRequestBody(cachedBodyHttpServletRequest);
//            System.out.println("Body: "+body);
//            filterChain.doFilter(cachedBodyHttpServletRequest, response);
//        } catch (Exception e){
//            System.out.println("Error test filter");
//            System.out.println(e);
//        }
//        filterChain.doFilter(request, response);
//    }
//}
