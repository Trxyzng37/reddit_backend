package com.trxyzng.trung.authentication.signin.controllers;

import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenService;
import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenUtil;
import com.trxyzng.trung.user.shared.UserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "http://127.0.0.1:4200", allowCredentials = "true")
@RestController
public class SignInController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String googleJwtToken = "";
    @Autowired
    RefreshTokenService refreshTokenService;
    @ResponseBody
    @RequestMapping(value = "/signin/username-password",method = RequestMethod.POST)
    public ResponseEntity<String> login() throws AuthenticationException {
        try {
            Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetail) {
                UserDetail user = (UserDetail) principal;
                int id = user.getId();
                //generate jwt token
                String token = RefreshTokenUtil.generateRefreshToken(id);
                System.out.println("Token using username password: " + token);
                refreshTokenService.saveRefreshToken(id, token);
                System.out.println("Save refresh_toke to database.");
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.SET_COOKIE, "refresh_token=" + token + "; Max-Age=100; SameSite=None; Secure; Path=/; Domain=127.0.0.1");
                ResponseEntity<String> responseEntity = new ResponseEntity<>(token, headers, HttpStatus.OK);
                return responseEntity;
            }
            else {
                System.out.println("Can not get username password");
            }
        }
        catch (DataIntegrityViolationException e) {
            logger.error("Error authenticating user using username password. Data integrity.");
//            System.out.println("Error authenticating user using username password. Data integrity.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error authenticate user using username password");
        }
        catch (AuthenticationException e){
            System.out.println("Error authenticating user using username password. Username or password is incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error authenticate user using username password");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Empty");
    }

    @ResponseBody
    @RequestMapping(value = "/signin/check-google-jwt-token", method = RequestMethod.GET)
    ResponseEntity isJwtToken()  {
        System.out.println("Check token: "+this.googleJwtToken);
        return ResponseEntity.ok(this.googleJwtToken);
    }
}

