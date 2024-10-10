package com.trxyzng.trung.search.user_profile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.trxyzng.trung.search.user_profile.pojo.UpdateUserInfoRequest;
import com.trxyzng.trung.utility.DefaultResponse;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserProfileController {
    @Autowired
    UserProfileService userProfileService;

    @Value("${photo_storage_url}")
    private String photo_storage_url;

    @RequestMapping(value = "/get-user-info-by-uid", method = RequestMethod.GET)
    public ResponseEntity<String> findUserProfilesByUid(@RequestParam("uid") int uid) {
        UserProfileEntity result = userProfileService.userProfileRepo.findByUid(uid).orElse(new UserProfileEntity(0,"","",null,0,0,""));
        String responseBody = JsonUtils.getStringFromObject(result);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/find-user-profile", method = RequestMethod.GET)
    public ResponseEntity<String> findUserProfilesByName(@RequestParam("name") String name) {
        UserProfileEntity[] result = userProfileService.findUserProfileEntitiesByName(name, 4);
        if (result.length == 0) {
            result = userProfileService.findUserProfileEntitiesIncludingKeyword(name, 100);
        }
        String responseBody = JsonUtils.getStringFromObject(result);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

//    @RequestMapping(value = "/get-user-info", method = RequestMethod.GET)
//    public ResponseEntity<String> getUserInfoByUid(@RequestParam("uid") int uid) {
//        UserProfileEntity result = userProfileService.findByUid(uid);
//        if (result.equals(null)) {
//            String responseBody = JsonUtils.getStringFromObject(result);
//            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
//        }
//        String responseBody = JsonUtils.getStringFromObject(result);
//        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
//    }

    @RequestMapping(value = "/get-user-info-by-username", method = RequestMethod.GET)
    public ResponseEntity<String> getUserInfoByUid(@RequestParam("username") String username) {
    		try {
    	        UserProfileEntity result = userProfileService.findByUsername(username);
    	        String responseBody = JsonUtils.getStringFromObject(result);
    	        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    		}
    		catch (Exception e) {
				System.out.print(e.getMessage());
				return new ResponseEntity<String>("Error get user info by username", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
    }

    @RequestMapping(value = "/edit-user-info", method = RequestMethod.POST)
    public ResponseEntity<String> updateUserProfileByUid(@RequestBody UpdateUserInfoRequest body) {
        try {
            Cloudinary cloudinary = new Cloudinary(photo_storage_url);
            cloudinary.config.secure = true;
            Map response = cloudinary.uploader().upload(
                    body.getAvatar(),
                    ObjectUtils.asMap(
                            "folder", "user_icon",
                            "use_filename", false,
                            "unique_filename", true,
                            "allowed_formats", "jpeg, jpg, png"
                    )
            );
            String imgUrl = (String) response.get("secure_url");
            userProfileService.UpdateUserProfile(body.getUid(), body.getDescription(), imgUrl);
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
