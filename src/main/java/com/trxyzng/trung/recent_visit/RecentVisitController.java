package com.trxyzng.trung.recent_visit;

import com.trxyzng.trung.post.PostEntity;
import com.trxyzng.trung.post.PostService;
import com.trxyzng.trung.post.get_post.pojo.GetPostResponse;
import com.trxyzng.trung.recent_visit.community.RecentVisitCommunityEntity;
import com.trxyzng.trung.recent_visit.community.RecentVisitCommunityRepo;
import com.trxyzng.trung.recent_visit.pojo.RecentVisitRequest;
import com.trxyzng.trung.recent_visit.post.RecentVisitPostEntity;
import com.trxyzng.trung.recent_visit.post.RecentVistPostRepo;
import com.trxyzng.trung.search.community.CommunityEntity;
import com.trxyzng.trung.search.community.CommunityService;
import com.trxyzng.trung.utility.DefaultResponse;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@RestController
public class RecentVisitController {
    @Autowired
    RecentVisitCommunityRepo recentVisitCommunityRepo;
    @Autowired
    RecentVistPostRepo recentVistPostRepo;
    @Autowired
    CommunityService communityService;
    @Autowired
    PostService postService;

    @RequestMapping(value = "set-recent-visit-community", method = RequestMethod.POST)
    public ResponseEntity<String> setRecentVisitCommunity(@RequestBody RecentVisitRequest body) {
        try {
            int found = recentVisitCommunityRepo.existsByUidAndCommunityId(body.getUid(), body.getVisit_id());
            if(found == 0) {
                recentVisitCommunityRepo.save(new RecentVisitCommunityEntity(body.getUid(), body.getVisit_id(), Instant.now().truncatedTo(ChronoUnit.MILLIS)));
            }
            else {
                recentVisitCommunityRepo.updateVisittedTime(body.getUid(), body.getVisit_id(), Instant.now().truncatedTo(ChronoUnit.MILLIS));
            }
            String responseBody = JsonUtils.getStringFromObject(new DefaultResponse(0, ""));
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            String responseBody = JsonUtils.getStringFromObject(new DefaultResponse(1, "error set recent visit community"));
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "set-recent-visit-post", method = RequestMethod.POST)
    public ResponseEntity<String> setRecentVisitPost(@RequestBody RecentVisitRequest body) {
        try {
            int found = recentVistPostRepo.existsByUidAndPostId(body.getUid(), body.getVisit_id());
            if(found == 0) {
                recentVistPostRepo.save(new RecentVisitPostEntity(body.getUid(), body.getVisit_id(), Instant.now().truncatedTo(ChronoUnit.MILLIS)));
            }
            else {
                System.out.println(("update"));
                recentVistPostRepo.updateVisittedTime(body.getUid(), body.getVisit_id(), Instant.now().truncatedTo(ChronoUnit.MILLIS));
            }
            String responseBody = JsonUtils.getStringFromObject(new DefaultResponse(0, ""));
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            String responseBody = JsonUtils.getStringFromObject(new DefaultResponse(1, "error set recent visit community"));
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "get-recent-visit-community", method = RequestMethod.GET)
    public ResponseEntity<String> getRecentVisitCommunity(@RequestParam("uid") int uid) {
        try {
            int[] arr = recentVisitCommunityRepo.findByUid(uid);
            ArrayList<CommunityEntity> results = new ArrayList<>();
            for(int i: arr) {
                CommunityEntity communityEntity = communityService.getCommunityEntityById(i);
                results.add(communityEntity);
            }
            String responseBody = JsonUtils.getStringFromObject(results);
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            String responseBody = JsonUtils.getStringFromObject(new DefaultResponse(1, "error get recent visit community for uid: "+uid));
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "get-recent-visit-post", method = RequestMethod.GET)
    public ResponseEntity<String> getRecentVisitPost(@RequestParam("uid") int uid) {
        try {
            int[] arr = recentVistPostRepo.findByUid(uid);
            ArrayList<GetPostResponse> results = new ArrayList<>();
            for(int i: arr) {
                if(postService.existsByPostId(i) == 1) {
                    GetPostResponse postEntity = postService.getPostResponseByPostId(i);
                    results.add(postEntity);
                }
            }
            String responseBody = JsonUtils.getStringFromObject(results);
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println("e: "+e);
            String responseBody = JsonUtils.getStringFromObject(new DefaultResponse(1, "error get recent visit community for uid: "+uid));
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
