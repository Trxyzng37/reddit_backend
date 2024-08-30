package com.trxyzng.trung.edit_community;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.trxyzng.trung.edit_community.pojo.EditCommunityRequest;
import com.trxyzng.trung.edit_community.pojo.EditCommunityResponse;
import com.trxyzng.trung.search.community.CommunityEntity;
import com.trxyzng.trung.search.community.CommunityService;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class EditCommunityController {
    @Autowired
    CommunityService communityService;

    @Value("${photo_storage_url}")
    private String photo_storage_url;

    @RequestMapping(value = "/edit-community", method = RequestMethod.POST)
    public ResponseEntity<String> editCommunity(@RequestBody EditCommunityRequest body) {
        try {
            Cloudinary cloudinary = new Cloudinary(photo_storage_url);
            cloudinary.config.secure = true;
            Map response = cloudinary.uploader().upload(
                    body.getAvatar(),
                    ObjectUtils.asMap(
                            "folder", "community_icon",
                            "use_filename", false,
                            "unique_filename", true,
                            "allowed_formats", "jpeg, jpg, png"
                    )
            );
            String communityIconUrl = (String) response.get("secure_url");
            Map bannerResponse = cloudinary.uploader().upload(
                    body.getBanner(),
                    ObjectUtils.asMap(
                            "folder", "community_banner",
                            "use_filename", false,
                            "unique_filename", true,
                            "allowed_formats", "jpeg, jpg, png"
                    )
            );
            String communityBannerUrl = (String) bannerResponse.get("secure_url");
            communityService.updateCommunityEntity(body.getCommunity_id(), body.getUid(), body.getDescription(), communityIconUrl, communityBannerUrl, body.getScope());
            System.out.println("Edit community id: "+body.getCommunity_id());
            String responseBody = JsonUtils.getStringFromObject(new EditCommunityResponse(true, 0));
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            String responseBody = JsonUtils.getStringFromObject(new EditCommunityResponse(false, 1));
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

    }
}
