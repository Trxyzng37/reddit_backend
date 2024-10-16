package com.trxyzng.trung.authentication.refreshtoken;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;

@RestController
public class RefreshTokenController {
    @Autowired
    RefreshTokenRepo refreshTokenRepo;
    @Value("${frontendAddress}")
    private String frontEndAddress;
    @Value("${fullFrontendAddress}")
    private String fullFrontendAddress;

    @RequestMapping(value="/check-refresh-token", method = RequestMethod.GET)
    public ResponseEntity<String> checkRefreshToken(HttpServletRequest request) {
        try {
            String refreshToken = RefreshTokenUtil.getRefreshTokenFromCookie(request);
            HttpHeaders headers = new HttpHeaders();
            if(refreshToken.length() != 0) {
                // int uid = refreshTokenRepo.selectUidByRefreshToken(refreshToken);
                Claims claim = RefreshTokenUtil.parseRefreshToken(refreshToken);
                String uid = claim.getSubject();
                return new ResponseEntity<>(uid, headers, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("0", headers, HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>("0", headers, HttpStatus.BAD_REQUEST);
        }
    }
}
