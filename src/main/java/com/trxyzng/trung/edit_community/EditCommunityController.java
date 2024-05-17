package com.trxyzng.trung.edit_community;

import com.trxyzng.trung.edit_community.pojo.EditCommunityRequest;
import com.trxyzng.trung.edit_community.pojo.EditCommunityResponse;
import com.trxyzng.trung.search.community.CommunityEntity;
import com.trxyzng.trung.search.community.CommunityService;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EditCommunityController {
    @Autowired
    CommunityService communityService;

    @RequestMapping(value = "/edit-community", method = RequestMethod.POST)
    public ResponseEntity<String> editCommunity(@RequestBody EditCommunityRequest body) {
        try {
            communityService.updateCommunityEntity(body.getCommunity_id(), body.getUid(), body.getDescription(), body.getAvatar(), body.getBanner(), body.getScope());
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
