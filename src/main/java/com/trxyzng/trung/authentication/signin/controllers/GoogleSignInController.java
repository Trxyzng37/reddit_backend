package com.trxyzng.trung.authentication.signin.controllers;

import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenService;
import com.trxyzng.trung.user.shared.services.UserByEmailService;
import com.trxyzng.trung.user.shared.UserDetail;
import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
            UserDetail uuser = (UserDetail) userByEmailService.loadUserByEmail(email);
            int id = uuser.getId();
            System.out.println("Id: " + id);
            String token = RefreshTokenUtil.generateRefreshToken(id);
            System.out.println("Email: " + email);
            System.out.println("Jwt token using email: " + token);
            refreshTokenService.SAVE_TOKEN(id, token);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("http://127.0.0.1:4200/home"));
            headers.add(HttpHeaders.SET_COOKIE, "refresh_token=" + token + "; Max-Age=100; SameSite=None; Secure; Path=/; Domain=127.0.0.1");
            ResponseEntity<String> responseEntity = new ResponseEntity<>(token, headers, HttpStatus.SEE_OTHER);
            return responseEntity;
        }
        catch (UsernameNotFoundException e){
            System.out.println("Can not find user with email using auth2.0");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Can not find user with email using auth2.0");
        }
        catch (NullPointerException e) {
            System.out.println("can not save user because it is null google");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Can not find user with email using auth2.0");
        }
    }
}
