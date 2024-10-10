package com.trxyzng.trung.join_community;

import com.trxyzng.trung.join_community.pojo.JoinCommunityRequest;
import com.trxyzng.trung.join_community.pojo.JoinCommunityResponse;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class JoinCommunityController {
    @Autowired
    JoinCommunityService joinCommunityService;

    @RequestMapping(value = "check-join-community", method = RequestMethod.GET)
    public ResponseEntity<String> checkJoinCommunity(@RequestParam("uid") int uid, @RequestParam("cid") int community_id) {
        try {
            int subscribed = joinCommunityService.getSubscribedStatus(uid, community_id);
            String responseBody = JsonUtils.getStringFromObject(new JoinCommunityResponse(subscribed, 0));
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            String responseBody = JsonUtils.getStringFromObject(new JoinCommunityResponse(0, 1));
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "join-community", method = RequestMethod.POST)
    public ResponseEntity<String> joinCommunity(@RequestBody JoinCommunityRequest requestBody) {
        try {
            if(requestBody.getUid() == 0) {
                String responseBody = JsonUtils.getStringFromObject(new JoinCommunityResponse(requestBody.getSubscribed(), 1));
                return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
            }
            else {
                joinCommunityService.saveOrUpdateJoinCommunityEntity(requestBody.getUid(), requestBody.getCommunity_id(), requestBody.getSubscribed());
                String responseBody = JsonUtils.getStringFromObject(new JoinCommunityResponse(requestBody.getSubscribed(), 0));
                return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
            }
        }
        catch (Exception e) {
            System.out.println(e);
            String responseBody = JsonUtils.getStringFromObject(new JoinCommunityResponse(requestBody.getSubscribed(), 1));
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
