package com.trxyzng.trung.search.community;

import com.trxyzng.trung.comment.CommentService;
import com.trxyzng.trung.join_community.JoinCommunityService;
import com.trxyzng.trung.post.PostRepo;
import com.trxyzng.trung.search.community.pojo.DeleteCommunityRequest;
import com.trxyzng.trung.search.community.pojo.UpdateSubscribedCountRequest;
import com.trxyzng.trung.utility.DefaultResponse;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class CommunityController {
    @Autowired
    PostRepo postRepo;
    @Autowired
    CommunityRepo communityRepo;
    @Autowired
    CommunityService communityService;
    @Autowired
    JoinCommunityService joinCommunityService;
    @Autowired
    CommentService commentService;

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

    @RequestMapping(value = "delete-community", method = RequestMethod.POST)
    public ResponseEntity<String> deleteCommunity(@RequestBody DeleteCommunityRequest body) {
        try {
            communityRepo.updateDeletedById(body.getId(), body.getUid(), body.getDeleted());
            int[] postIds = postRepo.selectPostIdByCommunityId(body.getId());
            commentService.deleteCommentsByPostId(postIds);
            postRepo.updateDeletedByCommunityId(body.getId());
            String responseBody = JsonUtils.getStringFromObject(new DefaultResponse(0, ""));
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            String responseBody = JsonUtils.getStringFromObject(new DefaultResponse(1, "error delete community"));
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
