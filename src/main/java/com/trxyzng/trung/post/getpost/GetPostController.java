package com.trxyzng.trung.post.getpost;

import com.trxyzng.trung.post.ErrorResponse;
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

    @RequestMapping(value = "/get-post", method = RequestMethod.GET)
    public ResponseEntity<String> findPostByPostId(@RequestParam("pid") int post_id) {
        if (postService.existsByPostId(post_id) == 1) {
            GetPostResponse post = postService.getPostByPostId(post_id);
            System.out.println("Get post with post_id: "+post_id);
            String responseBody = JsonUtils.getStringFromObject(post);
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        else {
            String responseBody = JsonUtils.getStringFromObject( new ErrorResponse(111, "post not exist", "can not find post with id: "+post_id));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/get-posts", method = RequestMethod.GET)
    public ResponseEntity<String> findAllPosts() {
        List<GetPostResponse> results = postService.getPostResponseFromId(0);
        String responseBody = JsonUtils.getStringFromObject(results);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }
}

