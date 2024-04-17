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

import java.util.List;

@RestController
public class GetPostController {
    @Autowired
    PostService postService;
    @Autowired
    CommunityRepo communityRepo;


    private CreatePostResponse createPostResponse;

    @RequestMapping(value = "/get-posts", method = RequestMethod.GET)
    public ResponseEntity<String> findAllPosts() {
        List<GetPostResponse> results = postService.getPostResponseFromId(0);
        String responseBody = JsonUtils.getStringFromObject(results);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }
}

