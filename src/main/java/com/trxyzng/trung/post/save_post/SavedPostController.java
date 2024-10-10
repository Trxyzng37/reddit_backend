package com.trxyzng.trung.post.save_post;

import com.trxyzng.trung.post.save_post.pojo.SavedPostRequest;
import com.trxyzng.trung.post.save_post.pojo.SavedPostResponse;
import com.trxyzng.trung.utility.DefaultResponse;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SavedPostController {
    @Autowired
    SavedPostService savedPostService;
    @Autowired
    SavedPostRepo savedPostRepo;

    @RequestMapping(value = "/save-post", method = RequestMethod.POST)
    public ResponseEntity<String> saveOrUpdatePost(@RequestBody SavedPostRequest body) {
        try {
            savedPostService.saveOrUpdateSavedPost(body.getUid(), body.getPost_id(), body.getSaved());
            String responseBody = JsonUtils.getStringFromObject(new DefaultResponse(0, ""));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e) {
            String responseBody = JsonUtils.getStringFromObject(new DefaultResponse(1, "error save post"));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/get-save-post", method = RequestMethod.GET)
    public int[] saveOrUpdatePost(@RequestParam int uid) {
        return savedPostService.getAllPostResponsesByUid(uid);
    }

    @RequestMapping(value = "/get-save-post-status", method = RequestMethod.GET)
    public ResponseEntity<String> getSavedPostStatus(@RequestParam int uid, @RequestParam int pid) {
        try {
            int savedStatus = 0;
            int exist = savedPostRepo.existsByPostIdAndUid(pid, uid);
            if(exist == 1) {
                savedStatus = savedPostRepo.selectSavedByUidAndPostId(uid, pid);
            }
            System.out.println("Save status: " + savedStatus);
            String responseBody = JsonUtils.getStringFromObject(new SavedPostResponse(savedStatus));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            String responseBody = JsonUtils.getStringFromObject(new DefaultResponse(1, "error get save post status"));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

}
