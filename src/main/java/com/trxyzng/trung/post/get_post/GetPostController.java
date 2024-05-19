package com.trxyzng.trung.post.get_post;

import com.trxyzng.trung.post.ErrorResponse;
import com.trxyzng.trung.post.PostEntity;
import com.trxyzng.trung.post.PostService;
import com.trxyzng.trung.post.get_post.pojo.GetPostResponse;
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

    @RequestMapping(value = "/get-post", method = RequestMethod.GET)
    public ResponseEntity<String> findPostByPostId(@RequestParam("pid") int post_id) {
        if (postService.existsByPostId(post_id) == 1) {
            GetPostResponse post = postService.getPostResponseByPostId(post_id);
            System.out.println("Get post with post_id: "+post_id);
            String responseBody = JsonUtils.getStringFromObject(post);
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        else {
            String responseBody = JsonUtils.getStringFromObject( new ErrorResponse(111, "post not exist", "can not find post with id: "+post_id));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/get-home-posts", method = RequestMethod.GET)
    public ResponseEntity<String> getHomePost(@RequestParam("uid") int uid, @RequestParam("sort") String sort) {
        List<GetPostResponse> results = postService.getAllPostsForHomeByUidAndSort(uid, sort);
        System.out.println("size: "+results.size());
        String responseBody = JsonUtils.getStringFromObject(results);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get-popular-posts", method = RequestMethod.GET)
    public ResponseEntity<String> getPopularPost(@RequestParam("uid") int uid, @RequestParam("sort") String sort) {
        List<GetPostResponse> results = postService.getAllPostsForPopularByUidAndSort(uid, sort);
        System.out.println("size: "+results.size());
        String responseBody = JsonUtils.getStringFromObject(results);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get-community-posts", method = RequestMethod.GET)
    public ResponseEntity<String> getCommunityPost(@RequestParam("cid") int cid, @RequestParam("sort") String sort) {
        List<GetPostResponse> results = postService.getAllPostsByCommunityIdAndSort(cid, sort);
        String responseBody = JsonUtils.getStringFromObject(results);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get-control-posts", method = RequestMethod.GET)
    public ResponseEntity<String> getControlPosts(@RequestParam("cid") int cid) {
        List<GetPostResponse> results = postService.getALlPostsByCOmmunityIdAndNotAllow(cid);
        String responseBody = JsonUtils.getStringFromObject(results);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<String> getCommunityPost() {
        List<GetPostResponse> results = postService.getAllPostsForPopularByUidAndSort(100088, "new");
        String responseBody = JsonUtils.getStringFromObject(results);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }
}

