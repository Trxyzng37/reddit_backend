package com.trxyzng.trung.authentication.signup.google;

import com.trxyzng.trung.authentication.shared.user.UserEntity;
import com.trxyzng.trung.authentication.shared.user.services.UserEntityService;
import com.trxyzng.trung.authentication.signup.pojo.GoogleSignUpResponse;
import com.trxyzng.trung.search.user_profile.UserProfileEntity;
import com.trxyzng.trung.search.user_profile.UserProfileRepo;
import com.trxyzng.trung.search.user_profile.UserProfileService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@CrossOrigin(origins = "http://127.0.0.1:4200", allowCredentials = "true")
@RestController
public class GoogleSignUpController {
    @Autowired
    UserEntityService userEntityService;
    @Autowired
    UserProfileRepo userProfileRepo;
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
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(fullFrontendAddress + "/signup"));
        boolean is_user = userEntityService.existByEmail(email);
        System.out.println("User empty: " + is_user);
        if (!is_user) {
            UserEntity userEntity = new UserEntity(email.substring(0,6), "password", email);
            UserEntity savedUserEntity = userEntityService.saveUserEntity(userEntity);
            userProfileRepo.save(new UserProfileEntity(savedUserEntity.getUid(), savedUserEntity.getUsername(), "Hi, my name is "+savedUserEntity.getUsername(), Instant.now().truncatedTo(ChronoUnit.MILLIS)));
            System.out.println("Save new user OK for google sign-up with email: "+email);
            System.out.println("uid of new user: " + savedUserEntity.getUid());
            GoogleSignUpResponse googleSignUpResponse = new GoogleSignUpResponse(true);
            String responseBody = JsonUtils.getStringFromObject(googleSignUpResponse);
            headers.add(HttpHeaders.SET_COOKIE, "signup_email=" + email + "; Max-Age=5; SameSite=None; Secure; Path=/; Domain=" + frontEndAddress);
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
