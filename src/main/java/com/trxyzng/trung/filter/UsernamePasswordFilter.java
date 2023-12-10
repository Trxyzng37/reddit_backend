package com.trxyzng.trung.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Component
public class UsernamePasswordFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationManager userPasswordAuthenticationManager;
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
//            String body = getRequestBody(request);
//            JsonObject json = Json.createReader(new StringReader(body)).readObject();
//            String user = json.getString("user");
//            String password = json.getString("password");
            ContentCachingRequestWrapper wrapper = new ContentCachingRequestWrapper(request);
            wrapper.getInputStream();
            String requestBody = wrapper.getContentAsString();
            System.out.println(requestBody);
            String body = "\"" + requestBody + "\"";
            String j = "{\"user\":\"t\", \"password\":\"1\"}";
            if (requestBody != "") {
                System.out.println(requestBody);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                String user = jsonNode.get("user").asText();
                String password = jsonNode.get("password").asText();
//                String password = jsonNode.get("password").asText();
//                JsonObject json = Json.createReader(new StringReader(body)).readObject();
//                String user = json.getString("user");
//                String password = json.getString("password");
                System.out.println(user);
                System.out.println(password);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, password);
                Authentication authentication = userPasswordAuthenticationManager
                        .authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }
            else {
                System.out.println("NULLLLLL");
            }
        } catch (AuthenticationException e) {
            System.out.println("Error authenticate using user pass: " + e.getMessage());
        }
//        catch(JsonException e){
//            System.out.println("JSON parse error");
//            System.out.println(e);
//        }
    }
}
