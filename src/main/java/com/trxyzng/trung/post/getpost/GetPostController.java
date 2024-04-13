package com.trxyzng.trung.post.getpost;

import com.trxyzng.trung.post.PostService;
import com.trxyzng.trung.post.create_post.pojo.CreatePostResponse;
import com.trxyzng.trung.post.getpost.pojo.GetPostResponse;
import com.trxyzng.trung.search.community.CommunityRepo;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GetPostController {
    @Autowired
    PostService postService;
    @Autowired
    CommunityRepo communityRepo;


    private CreatePostResponse createPostResponse;

    @RequestMapping(value = "/get-posts", method = RequestMethod.GET)
    public ResponseEntity<String> findAllPosts() {
        int[] id_arr = {100085, 100086, 100092, 100105};
        GetPostResponse[] results = new GetPostResponse[3];
        for(int i=0; i< id_arr.length; i++) {
            GetPostResponse response = postService.getPostResponseFromId(id_arr[i]);
            results[i] = response;
        }
        String responseBody = JsonUtils.getStringFromObject(results);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }
}
