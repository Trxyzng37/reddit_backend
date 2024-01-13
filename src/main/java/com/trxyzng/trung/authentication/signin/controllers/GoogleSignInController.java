package com.trxyzng.trung.authentication.signin.controllers;

import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenService;
import com.trxyzng.trung.user.shared.services.UserByEmailService;
import com.trxyzng.trung.user.shared.UserDetail;
import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenUtil;
import com.trxyzng.trung.utility.EmptyEntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@CrossOrigin(origins = "http://127.0.0.1:4200", allowCredentials = "true")
@RestController
public class GoogleSignInController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserByEmailService userByEmailService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @RequestMapping(value="/signin/google-authentication", method = RequestMethod.GET)
    public ResponseEntity<String> user() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            DefaultOidcUser user = (DefaultOidcUser) authentication.getPrincipal();
            String email = user.getEmail();
            System.out.println("Find user with email: " + email);
            UserDetail uuser = userByEmailService.loadUserByEmail(email);
            if (EmptyEntityUtils.isEmptyEntity(uuser.getUserEntity())) {
                System.out.println("Can not find user with email using auth2.0");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Can not find user with email using auth2.0");
            }
            int uid = uuser.getId();
            System.out.println("Find user with email " + email + " with id " + uid);
            String token = RefreshTokenUtil.generateRefreshToken(uid);
            System.out.println("Jwt token using email: " + token);
            refreshTokenService.saveRefreshToken(uid, token);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, "refresh_token=" + token + "; Max-Age=100; SameSite=None; Secure; Path=/; Domain=127.0.0.1");
            ResponseEntity<String> responseEntity = new ResponseEntity<>(token, headers, HttpStatus.OK);
            return responseEntity;
        }
        catch (DataIntegrityViolationException e) {
            logger.error("Error authenticating user using google sign-in. Data integrity.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error authenticate user using google sign-in");
        }
    }
}
