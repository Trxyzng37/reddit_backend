package com.trxyzng.trung.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.*;

@Component
public class UsernamePasswordFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationManager userPasswordAuthenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, NullPointerException {
        try {
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
                    new CachedBodyHttpServletRequest(request);
            String b = readRequestBody(cachedBodyHttpServletRequest);
                System.out.println("F: " + b);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(b);
                    String user = jsonNode.get("user").asText();
                    String password = jsonNode.get("password").asText();
                    System.out.println(user);
                    System.out.println(password);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(user, password);
                    Authentication authentication = userPasswordAuthenticationManager
                            .authenticate(authenticationToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (NullPointerException e) {
            System.out.println("Error for json");
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
                    new CachedBodyHttpServletRequest(request);
            filterChain.doFilter(cachedBodyHttpServletRequest, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    public String readRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (InputStream inputStream = request.getInputStream();
             BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }
        return requestBody.toString();
    }
}
