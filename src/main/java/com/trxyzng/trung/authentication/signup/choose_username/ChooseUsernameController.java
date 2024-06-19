package com.trxyzng.trung.authentication.signup.choose_username;

import com.trxyzng.trung.authentication.shared.user.UserEntity;
import com.trxyzng.trung.authentication.shared.user.UserEntityRepo;
import com.trxyzng.trung.authentication.shared.user.services.UserEntityService;
import com.trxyzng.trung.authentication.signup.choose_username.pojo.SelectUsernameRequest;
import com.trxyzng.trung.authentication.signup.choose_username.pojo.UserNameExistResponse;
import com.trxyzng.trung.authentication.signup.pojo.GoogleSignUpResponse;
import com.trxyzng.trung.utility.DefaultResponse;
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

@RestController
public class ChooseUsernameController {
    @Autowired
    UserEntityRepo userEntityRepo;
    @Autowired
    UserEntityService userEntityService;
    @Value("${frontendAddress}")
    private String frontEndAddress;
    @Value("${fullFrontendAddress}")
    private String fullFrontendAddress;
    @RequestMapping(value = "check-username", method = RequestMethod.GET)
    public ResponseEntity<String> checkUsername(@RequestParam String username) {
        try {
            int exist = userEntityService.isUsernameExist(username);
            boolean response = exist == 1 ? true : false;
            HttpHeaders headers = new HttpHeaders();
            UserNameExistResponse userNameExistResponse = new UserNameExistResponse(response);
            String responseBody = JsonUtils.getStringFromObject(userNameExistResponse);
            return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println("check username error: "+e);
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(e.toString(), headers, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "select-username", method = RequestMethod.POST)
    public ResponseEntity<String> selectUsername(@RequestBody SelectUsernameRequest body) {
        try {
            userEntityRepo.UpdateUsernameByEmail(body.getEmail(), body.getUsername());
            HttpHeaders headers = new HttpHeaders();
            DefaultResponse defaultResponse = new DefaultResponse(0, "");
            String responseBody = JsonUtils.getStringFromObject(defaultResponse);
            return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println("select username error: "+e);
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(e.toString(), headers, HttpStatus.BAD_REQUEST);
        }
    }
}
