package com.trxyzng.trung.authentication.accesstoken;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://127.0.0.1:4200", allowCredentials = "true")
@RestController
public class AccessTokenController {
    @Autowired
    AccessTokenService accessTokenService;
    @RequestMapping(value = "/get-access-token", method = RequestMethod.GET)
    public ResponseEntity<String> getAccessToken() {
        String accessToken = AccessTokenUtil.generateAccessToken(1, "test");
        System.out.println("Get access_token");
        System.out.println("access_token: " + accessToken);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(accessToken, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/check-access-token", method = RequestMethod.GET)
    public ResponseEntity<String> checkAccessToken(HttpServletRequest request) {
        String accessToken = AccessTokenUtil.getAccessTokenFromHeader(request);
        System.out.println("Check access_token AccessTokenController");
        System.out.println("access_token: " + accessToken);
        System.out.println("Access token is OK");
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>("access_token is OK", headers, HttpStatus.OK);
    }
}
