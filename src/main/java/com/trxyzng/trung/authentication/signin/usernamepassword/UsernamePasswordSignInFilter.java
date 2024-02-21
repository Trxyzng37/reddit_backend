package com.trxyzng.trung.authentication.signin.usernamepassword;

import com.trxyzng.trung.authentication.signin.pojo.UsernamePasswordSignInRequest;
import com.trxyzng.trung.authentication.signin.pojo.UsernamePasswordSignInResponse;
import com.trxyzng.trung.utility.EmptyObjectUtils;
import com.trxyzng.trung.utility.JsonUtils;
import com.trxyzng.trung.utility.servlet.HttpServletRequestUtils;
import com.trxyzng.trung.utility.servlet.CachedBodyHttpServletRequest;
import com.trxyzng.trung.utility.BeanUtils;
import com.trxyzng.trung.utility.servlet.HttpServletResponseUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.*;

/**
 read body request as json, get username and password
 check if the information is correct
 if correct put authentication object into security context holder
 else jump to next filter
 */
public class UsernamePasswordSignInFilter extends OncePerRequestFilter {
    private final AuthenticationManager userPasswordAuthenticationManager = BeanUtils.getBean(AuthenticationManager.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, NullPointerException {
        try {
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
                    new CachedBodyHttpServletRequest(request);
            String requestBody = HttpServletRequestUtils.readRequestBody(cachedBodyHttpServletRequest);
            System.out.println("Body of request: " + requestBody);
            UsernamePasswordSignInRequest jsonObj =
                    JsonUtils.getObjectFromString(requestBody, UsernamePasswordSignInRequest.class, UsernamePasswordSignInRequest::new);
            if (EmptyObjectUtils.is_empty(jsonObj)) {
                System.out.println("Error get object from string");
                UsernamePasswordSignInResponse signInResponse = new UsernamePasswordSignInResponse(false, false);
                String responseBody = JsonUtils.getStringFromObject(signInResponse);
                if (responseBody.equals(""))
                    HttpServletResponseUtils.sendResponseToClient(response, 400, "application/json", "UTF-8", "error get string from object");
                HttpServletResponseUtils.sendResponseToClient(response, 200, "application/json", "UTF-8", responseBody);
            }
            else {
                String username = jsonObj.getUsername();
                String password = jsonObj.getPassword();
                System.out.println(username);
                System.out.println(password);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, password);
                Authentication authentication = this.userPasswordAuthenticationManager.authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Successfully authenticate user");
                filterChain.doFilter(cachedBodyHttpServletRequest, response);
            }
        } catch (AuthenticationException e) {
            System.out.println("Error authenticate user using username password username password filter");
            UsernamePasswordSignInResponse login = new UsernamePasswordSignInResponse(false, true);
            String responseBody = JsonUtils.getStringFromObject(login);
            if (responseBody.equals(""))
                HttpServletResponseUtils.sendResponseToClient(response, 400, "application/json", "UTF-8", "error get string from object");
            HttpServletResponseUtils.sendResponseToClient(response, 200, "application/json", "UTF-8", responseBody);
        }
    }
}
