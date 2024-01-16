package com.trxyzng.trung.authentication.signin.filter;

import com.trxyzng.trung.user.shared.UserDetail;
import com.trxyzng.trung.user.shared.services.UserByEmailService;
import com.trxyzng.trung.utility.BeanUtils;
import com.trxyzng.trung.utility.EmptyEntityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class GoogleSignInFilter extends OncePerRequestFilter {
    private final UserByEmailService userByEmailService = BeanUtils.getBean(UserByEmailService.class);
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, NullPointerException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DefaultOidcUser user = (DefaultOidcUser) authentication.getPrincipal();
        String email = user.getEmail();
        UserDetail uuser = userByEmailService.loadUserByEmail(email);
        if (EmptyEntityUtils.isEmptyEntity(uuser.getUserEntity())) {
            System.out.println("Can not find user with email using auth2.0");
            response.sendError(401, "Error authenticate user using google filter");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
