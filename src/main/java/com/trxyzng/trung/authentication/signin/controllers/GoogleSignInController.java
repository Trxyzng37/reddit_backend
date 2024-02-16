package com.trxyzng.trung.authentication.signin.controllers;

import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenService;
import com.trxyzng.trung.authentication.shared.user.services.UserByEmailService;
import com.trxyzng.trung.authentication.shared.user.UserDetail;
import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://127.0.0.1:4200", allowCredentials = "true")
@RestController
public class GoogleSignInController {
    @Autowired
    private UserByEmailService userByEmailService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @RequestMapping(value="/signin/google-authentication", method = RequestMethod.GET)
    public ResponseEntity<String> user() {
//        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            DefaultOidcUser user = (DefaultOidcUser) authentication.getPrincipal();
            String email = user.getEmail();
            System.out.println("Find user with email: " + email);
            UserDetail uuser = userByEmailService.loadUserByEmail(email);
            int uid = uuser.getId();
            System.out.println("Find user with email " + email + " with id " + uid);
            String token = RefreshTokenUtil.generateRefreshToken(uid);
            System.out.println("Refresh_token using email: " + token);
            refreshTokenService.saveRefreshToken(uid, token);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, "refresh_token=" + token + "; Max-Age=100; SameSite=None; Secure; Path=/; Domain=127.0.0.1");
            return new ResponseEntity<>(token, headers, HttpStatus.OK);
//        }
    }
}
