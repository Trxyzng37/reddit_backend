package com.trxyzng.trung.search.user_profile;

import com.trxyzng.trung.search.user_profile.pojo.UpdateUserInfoRequest;
import com.trxyzng.trung.utility.DefaultResponse;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserProfileController {
    @Autowired
    UserProfileService userProfileService;

    @RequestMapping(value = "/find-user-profile", method = RequestMethod.GET)
    public ResponseEntity<String> findUserProfilesByName(@RequestParam("name") String name) {
        UserProfileEntity[] result = userProfileService.findUserProfileEntitiesByName(name, 4);
        if (result.length == 0) {
            result = userProfileService.findUserProfileEntitiesIncludingKeyword(name, 100);
        }
        String responseBody = JsonUtils.getStringFromObject(result);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get-user-info", method = RequestMethod.GET)
    public ResponseEntity<String> getUserInfoByUid(@RequestParam("uid") int uid) {
        UserProfileEntity result = userProfileService.findByUid(uid);
        if (result.equals(null)) {
            String responseBody = JsonUtils.getStringFromObject(result);
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        String responseBody = JsonUtils.getStringFromObject(result);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get-user-info-by-username", method = RequestMethod.GET)
    public ResponseEntity<String> getUserInfoByUid(@RequestParam("username") String username) {
        UserProfileEntity result = userProfileService.findByUsername(username);
        if (result.equals(null)) {
            String responseBody = JsonUtils.getStringFromObject(result);
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        String responseBody = JsonUtils.getStringFromObject(result);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/edit-user-info", method = RequestMethod.POST)
    public ResponseEntity<String> updateUserProfileByUid(@RequestBody UpdateUserInfoRequest body) {
        try {
            userProfileService.UpdateUserProfile(body.getUid(), body.getDescription(), body.getAvatar());
            String responseBody = JsonUtils.getStringFromObject(new DefaultResponse(0, ""));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            String responseBody = JsonUtils.getStringFromObject(new DefaultResponse(1, "error update user info"));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
