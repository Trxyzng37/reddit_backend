package com.trxyzng.trung.search.community;

import com.trxyzng.trung.join_community.JoinCommunityService;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SearchCommunityController {
    @Autowired
    CommunityService communityService;

    @Autowired
    JoinCommunityService joinCommunityService;

    @RequestMapping(value = "/find-community", method = RequestMethod.GET)
    public ResponseEntity<String> findCommunitiesByName(@RequestParam("name") String name) {
        CommunityEntity[] result = communityService.findCommunityEntitiesByName(name, 4);
        if (result.length == 0) {
            result = communityService.findCommunityEntitiesIncludeByName(name, 100);
        }
        String responseBody = JsonUtils.getStringFromObject(result);
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "get-community-info", method = RequestMethod.GET)
    public ResponseEntity<String> findCommunityInfoById(@RequestParam("id") int id) {
        CommunityEntity result = communityService.getCommunityEntityById(id);
        String responseBody = JsonUtils.getStringFromObject(result);
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "get-subscribed-communities", method = RequestMethod.GET)
    public ResponseEntity<String> getSubscribedCommunities(@RequestParam("uid") int uid) {
        int[] cid = joinCommunityService.findAllJoinedCommunitiesByUid(uid);
        ArrayList<CommunityEntity> result = new ArrayList<CommunityEntity>();
        for(int i: cid) {
            System.out.println(i);
            result.add(communityService.getCommunityEntityById(i));
        }
        String responseBody = JsonUtils.getStringFromObject(result);
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "get-community-info-by-uid", method = RequestMethod.GET)
    public ResponseEntity<String> findCommunityInfoByUid(@RequestParam("uid") int uid) {
        CommunityEntity[] result = communityService.findCommunityEntitiesByUid(uid);
        System.out.println("length: "+result.length);
        String responseBody = JsonUtils.getStringFromObject(result);
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }
}
