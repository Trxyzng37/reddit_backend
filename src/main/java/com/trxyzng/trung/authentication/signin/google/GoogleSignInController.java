package com.trxyzng.trung.authentication.signin.google;

import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenService;
import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenUtil;
import com.trxyzng.trung.authentication.shared.user.UserEntity;
import com.trxyzng.trung.authentication.shared.user.services.UserEntityService;
import com.trxyzng.trung.authentication.signin.pojo.Login;
import com.trxyzng.trung.utility.EmptyEntityUtils;
import com.trxyzng.trung.utility.JsonUtils;
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
    private UserEntityService userEntityService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @RequestMapping(value="/signin/google-authentication", method = RequestMethod.GET)
    public ResponseEntity<String> user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DefaultOidcUser oathUser = (DefaultOidcUser) authentication.getPrincipal();
        String email = oathUser.getEmail();
        UserEntity user = userEntityService.findUserEntityByEmail(email);
        if (EmptyEntityUtils.isEmptyEntity(user)) {
            String response = "{\"googleLogin\":\"false\"}";
            Login o = new Login(false);
            String res = JsonUtils.getStringFromObject(o);
            HttpHeaders headers = new HttpHeaders();
            System.out.println(response);
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        }
        else {
            int uid = user.getId();
            System.out.println("Find user with email " + email + " with id " + uid);
            String token = RefreshTokenUtil.generateRefreshToken(uid);
            System.out.println("Refresh_token using email: " + token);
            refreshTokenService.saveRefreshToken(uid, token);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, "refresh_token=" + token + "; Max-Age=100; SameSite=None; Secure; Path=/; Domain=127.0.0.1");
            return new ResponseEntity<>(token, headers, HttpStatus.OK);
        }
    }
}
