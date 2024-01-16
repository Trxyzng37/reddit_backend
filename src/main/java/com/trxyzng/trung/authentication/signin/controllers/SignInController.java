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
    private String googleJwtToken = "";
    @Autowired
    RefreshTokenService refreshTokenService;
    @ResponseBody
    @RequestMapping(value = "/signin/username-password",method = RequestMethod.POST)
    public ResponseEntity<String> login() throws AuthenticationException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetail user = (UserDetail) principal;
        int uid = user.getId();
        //generate jwt token
        String token = RefreshTokenUtil.generateRefreshToken(uid);
        System.out.println("Token using username password: " + token);
        refreshTokenService.saveRefreshToken(uid, token);
        System.out.println("Save refresh_toke to database.");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, "refresh_token=" + token + "; Max-Age=120; SameSite=None; Secure; Path=/; Domain=127.0.0.1; HttpOnly");
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Login using username-password successfully", headers, HttpStatus.OK);
        return responseEntity;
    }
}

