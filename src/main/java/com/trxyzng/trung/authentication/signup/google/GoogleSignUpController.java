package com.trxyzng.trung.authentication.signup.google;

import com.trxyzng.trung.authentication.shared.oathuser.OathUserEntity;
import com.trxyzng.trung.authentication.shared.oathuser.OathUserEntityService;
import com.trxyzng.trung.authentication.shared.user.UserEntity;
import com.trxyzng.trung.authentication.shared.user.services.UserEntityService;
import com.trxyzng.trung.authentication.signup.pojo.GoogleSignUpResponse;
import com.trxyzng.trung.utility.EmptyEntityUtils;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@CrossOrigin(origins = "http://127.0.0.1:4200", allowCredentials = "true")
@RestController
public class GoogleSignUpController {
    @Autowired
    OathUserEntityService oathUserEntityService;
    @Autowired
    UserEntityService userEntityService;
    @Value("${frontendAddress}")
    private String frontEndAddress;
    @Value("${fullFrontendAddress}")
    private String fullFrontendAddress;
    @RequestMapping(value = "/signup/google", method = RequestMethod.GET)
    public ResponseEntity<String> UsernamePasswordSignUp() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DefaultOidcUser user = (DefaultOidcUser) authentication.getPrincipal();
        String email = user.getEmail();
        System.out.println("Email: " + email);
        OathUserEntity isEmailExistInOathUserDB = oathUserEntityService.findOathUserEntityByEmail(email);
        System.out.println("OathUser email: " + isEmailExistInOathUserDB.getEmail());
        UserEntity isEmailExistInUserDB = userEntityService.findUserEntityByEmail(email);
        System.out.println("User email: " + isEmailExistInUserDB.getEmail());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(fullFrontendAddress + "/signup"));
        boolean is_oath_user = EmptyEntityUtils.isEmptyEntity(isEmailExistInOathUserDB);
        boolean is_user = EmptyEntityUtils.isEmptyEntity(isEmailExistInUserDB);
        System.out.println("User empty: " + is_user);
        System.out.println("OathUser empty: " + is_oath_user);
        if (is_user && is_oath_user) {
            OathUserEntity oathUserEntity = new OathUserEntity(email);
            OathUserEntity savedOathUserEntity =
                    oathUserEntityService.saveOathUserEntity(oathUserEntity);
            System.out.println("Save new OathUser OK");
            System.out.println("uid of new user: " + savedOathUserEntity.getId());
            GoogleSignUpResponse googleSignUpResponse = new GoogleSignUpResponse(true);
            String responseBody = JsonUtils.getStringFromObject(googleSignUpResponse);
            headers.add(HttpHeaders.SET_COOKIE, "signup=" + responseBody + "; Max-Age=5; SameSite=None; Secure; Path=/; Domain=" + frontEndAddress);
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }
        else {
            System.out.println("Email already exist in database");
            GoogleSignUpResponse googleSignUpResponse = new GoogleSignUpResponse(false);
            String responseBody = JsonUtils.getStringFromObject(googleSignUpResponse);
            headers.add(HttpHeaders.SET_COOKIE, "signup=" + responseBody + "; Max-Age=5; SameSite=None; Secure; Path=/; Domain=" + frontEndAddress);
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }
    }
}
