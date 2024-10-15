package com.trxyzng.trung.authentication.signup.choose_username;

import com.trxyzng.trung.authentication.shared.user.UserEntityRepo;
import com.trxyzng.trung.authentication.shared.user.services.UserEntityService;
import com.trxyzng.trung.authentication.signup.choose_username.pojo.SelectUsernameRequest;
import com.trxyzng.trung.authentication.signup.choose_username.pojo.UserNameExistResponse;
import com.trxyzng.trung.search.user_profile.UserProfileRepo;
import com.trxyzng.trung.utility.DefaultResponse;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChooseUsernameController {
    @Autowired
    UserEntityRepo userEntityRepo;
    @Autowired
    UserEntityService userEntityService;
    @Autowired
    UserProfileRepo userProfileRepo;
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
            HttpHeaders headers = new HttpHeaders();
            int userNameExist = userEntityService.isUsernameExist(body.getUsername());
            if(userNameExist == 1) {
                DefaultResponse defaultResponse = new DefaultResponse(1, "username exist");
                String responseBody = JsonUtils.getStringFromObject(defaultResponse);
                return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
            }
            else {
                userEntityRepo.UpdateUsernameByEmail(body.getEmail(), body.getUsername());
                int uid = userEntityRepo.findUidByEmail(body.getEmail());
                System.out.println("uid: "+uid);
                userProfileRepo.UpdateUsernameByUid(uid, body.getUsername());
                userProfileRepo.UpdateDescriptionByUid(uid, "Hi, my name is "+body.getUsername());
                DefaultResponse defaultResponse = new DefaultResponse(0, "");
                String responseBody = JsonUtils.getStringFromObject(defaultResponse);
                return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
            }
        }
        catch (Exception e) {
            System.out.println("select username error: "+e);
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(e.toString(), headers, HttpStatus.BAD_REQUEST);
        }
    }
}
