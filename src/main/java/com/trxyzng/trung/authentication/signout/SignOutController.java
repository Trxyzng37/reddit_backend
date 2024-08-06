package com.trxyzng.trung.authentication.signout;

import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignOutController {
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Value("${frontendAddress}")
    private String frontEndAddress;

    @RequestMapping(value="/sign-out", method = RequestMethod.GET)
    public ResponseEntity<String> signOut() {
        try {
            String refresh_token = "";
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, "refresh_token=" + refresh_token + "; Max-Age=0; SameSite=None; Secure; Path=/; HttpOnly; " +"Domain=" + frontEndAddress);
            return new ResponseEntity<>("", headers, HttpStatus.OK);
        }
        catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, "refresh_token=" + "" + "; Max-Age=0; SameSite=None; Secure; Path=/; HttpOnly; " +"Domain=" + frontEndAddress);
            return new ResponseEntity<>("", headers, HttpStatus.BAD_REQUEST);
        }
    }
}
