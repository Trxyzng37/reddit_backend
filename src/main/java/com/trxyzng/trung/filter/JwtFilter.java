//package com.trxyzng.trung.filter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.trxyzng.trung.authentication.accesstoken.AccessTokenUtil;
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
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
////@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    private final com.trxyzng.trung.authentication.accesstoken.AccessTokenUtil AccessTokenUtil;
//
//    public JwtFilter(AccessTokenUtil accessTokenUtil, ObjectMapper mapper) {
//        this.AccessTokenUtil = accessTokenUtil;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        try {
//            String accessToken = (String) AccessTokenUtil.getAccessTokenFromHeader(request);
//            if (accessToken == null ) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//            System.out.println("JWT filter");
//            System.out.println("token : " + accessToken);
//            Claims claims = AccessTokenUtil.getBodyClaims(request);
//
//            if(claims != null & AccessTokenUtil.validateExpiration(claims)){
//                String user = (String) claims.get("user");
//                Authentication authentication =
//                        new UsernamePasswordAuthenticationToken(user,"",new ArrayList<>());
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }catch (Exception e){
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        }
//        filterChain.doFilter(request, response);
//    }
//}
