package com.trxyzng.trung.controller;

import com.trxyzng.trung.filter.AccessToken;
import com.trxyzng.trung.refresh_token_server.utility.RefreshTokenUtils;
import com.trxyzng.trung.repository.UserRepo;
import com.trxyzng.trung.service.userdetail.RefreshTokenService;
import com.trxyzng.trung.service.userdetail.UserByEmailService;
import com.trxyzng.trung.service.userdetail.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.security.Principal;

@CrossOrigin(origins = "http://127.0.0.1:4200", allowCredentials = "true")
@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager userPasswordAuthenticationManager;

    @Autowired
    private AccessToken AccessToken;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserByEmailService userByEmailService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private String googleJwtToken = "";

    @Value("${server.address}")
    private String address;

    @ResponseBody
    @RequestMapping(value = "/signin/username-password",method = RequestMethod.POST)
    public ResponseEntity<String> login() throws AuthenticationException {
        try {
            Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetail) {
                UserDetail user = (UserDetail) principal;
                int id = user.getId();
                String password = user.getPassword();
                //generate jwt token
//                String token = AccessToken.generateAccessToken(id, password);
                String token = RefreshTokenUtils.generateRefreshToken(id);
                System.out.println("Token using username password: " + token);
//                System.out.println("IP address " + address);
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.SET_COOKIE, "refresh_token=" + token + "; Max-Age=100; SameSite=None; Secure; Path=/; Domain=127.0.0.1");
                ResponseEntity<String> responseEntity = new ResponseEntity<>(token, headers, HttpStatus.OK);
                return responseEntity;
            }
            else {
                System.out.println("Can not get username password");
            }
        }
        catch (AuthenticationException e){
            System.out.println("Error authenticating user. Username or password is incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error authenticate user using username password");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Empty");
    }

    @RequestMapping(value="/signin/google-authentication", method = RequestMethod.GET)
    public ResponseEntity<String> user() {
//        @AuthenticationPrincipal OAuth2User authenticate_user
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            DefaultOidcUser user = (DefaultOidcUser) authentication.getPrincipal();
            String email = user.getEmail();
            UserDetail uuser = (UserDetail) userByEmailService.loadUserByUsername(email);
                int id = uuser.getId();
                System.out.println("Id: " + id);
                String token = RefreshTokenUtils.generateRefreshToken(id);
                System.out.println("Email: " + email);
                System.out.println("Jwt token using email: " + token);
                this.googleJwtToken = token;
                refreshTokenService.saveTokenForUser(id, token);

//                refreshTokenService.SaveRefreshToken(s);
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(URI.create("http://127.0.0.1:4200/home"));
                return ResponseEntity.status(HttpStatus.SEE_OTHER)
                        .headers(headers)
                        .body(this.googleJwtToken);
        }
        catch (UsernameNotFoundException e){
            System.out.println("Can not find user with email using auth2.0");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Can not find user with email using auth2.0");
        }
        catch (NullPointerException e) {
            System.out.println("No principal in Security context google");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Can not find user with email using auth2.0");
        }

    }

    @ResponseBody
    @RequestMapping(value = "/signin/check-google-jwt-token", method = RequestMethod.GET)
    ResponseEntity isJwtToken()  {
        System.out.println("Check token: "+this.googleJwtToken);
        return ResponseEntity.ok(this.googleJwtToken);
    }

}

