package com.trxyzng.trung.authentication.signin.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trxyzng.trung.utility.JsonUtils;
import com.trxyzng.trung.utility.RequestUtils;
import com.trxyzng.trung.utility.servlet.CachedBodyHttpServletRequest;
import com.trxyzng.trung.utility.BeanUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.*;

/**
 read body request as json, get username and password
 check if the information is correct
 if correct put authentication object into security context holder
 else jump to next filter
 */
public class UsernamePasswordSignInFilter extends OncePerRequestFilter {
    private PasswordEncoder passwordEncoder = BeanUtils.getBean(PasswordEncoder.class);
    private AuthenticationManager userPasswordAuthenticationManager = BeanUtils.getBean(AuthenticationManager.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, NullPointerException {
        try {
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
                    new CachedBodyHttpServletRequest(request);
            String body = RequestUtils.readRequestBody(cachedBodyHttpServletRequest);
                System.out.println("Body of request: " + body);
                JsonNode jsonNode = JsonUtils.getJsonObject(body);
                    String user = JsonUtils.readJsonProperty(jsonNode, "username");
                    String password = JsonUtils.readJsonProperty(jsonNode, "password");
                    System.out.println(user);
                    System.out.println(password);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(user, password);
                    Authentication authentication = this.userPasswordAuthenticationManager.authenticate(authenticationToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("Successfully authenticate user");
            filterChain.doFilter(cachedBodyHttpServletRequest, response);
        } catch (AuthenticationException e) {
            System.out.println("Error authenticate user using username password username password filter");
            response.sendError(401, "Error authenticate user using username password");
        }
    }
}
