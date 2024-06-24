package com.trxyzng.trung.authentication.refreshtoken;

import com.trxyzng.trung.authentication.shared.user.UserEntityRepo;
import com.trxyzng.trung.authentication.shared.user.services.UserEntityService;
import com.trxyzng.trung.authentication.signin.pojo.GoogleSignInResponse;
import com.trxyzng.trung.utility.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class RefreshTokenController {
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Value("${frontendAddress}")
    private String frontEndAddress;
    @Value("${fullFrontendAddress}")
    private String fullFrontendAddress;

    @RequestMapping(value="/check-refresh-token", method = RequestMethod.GET)
    public ResponseEntity<String> checkRefreshToken() {
//        try {
//            String refreshToken = RefreshTokenUtil.getRefreshTokenFromCookie(request);
//            System.out.println("Refresh-token: "+refreshToken);
//            HttpHeaders headers = new HttpHeaders();
//            if(refreshToken.length() == 0) {
//                return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
//            }
//            else {
//                return new ResponseEntity<>(headers, HttpStatus.OK);
//            }
//        }
//        catch (Exception e) {
//            HttpHeaders headers = new HttpHeaders();
//            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
//        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
