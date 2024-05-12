package com.trxyzng.trung.create_community;

import com.trxyzng.trung.authentication.shared.user.services.UserEntityService;
import com.trxyzng.trung.create_community.pojo.CreateCommunityRequest;
import com.trxyzng.trung.create_community.pojo.CreateCommunityResponse;
import com.trxyzng.trung.post.check_vote_post.pojo.CheckVotePostResponse;
import com.trxyzng.trung.search.community.CommunityEntity;
import com.trxyzng.trung.search.community.CommunityService;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CreateCommunityController {
    @Autowired
    CommunityService communityService;

    @RequestMapping(value = "/create-community", method = RequestMethod.POST)
    public ResponseEntity<String> votePost(@RequestBody CreateCommunityRequest requestBody) {
        try {
            boolean isNameExist = communityService.isCommunityEntityByNameExist(requestBody.getName());
            if(isNameExist) {
                String responseBody = JsonUtils.getStringFromObject(new CreateCommunityResponse(0, 1));
                return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
            }
            CommunityEntity communityEntity = new CommunityEntity(requestBody.getUid(),
                                                                  requestBody.getName(),
                                                                  requestBody.getDescription(),
                                                                  requestBody.getAvatar(),
                                                                  requestBody.getBanner()
            );
            CommunityEntity saved = communityService.saveCommunityEntity(communityEntity);
            String responseBody = JsonUtils.getStringFromObject(new CreateCommunityResponse(saved.getId(), 0));
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e) {
            String responseBody = JsonUtils.getStringFromObject(new CreateCommunityResponse(0, 2));
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
