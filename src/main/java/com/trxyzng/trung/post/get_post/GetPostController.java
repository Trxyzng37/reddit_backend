package com.trxyzng.trung.post.get_post;

import com.trxyzng.trung.post.ErrorResponse;
import com.trxyzng.trung.post.PostRepo;
import com.trxyzng.trung.post.PostService;
import com.trxyzng.trung.post.get_post.pojo.GetDetailPostResponse;
import com.trxyzng.trung.post.get_post.pojo.GetPostResponse;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GetPostController {
    @Autowired
    PostService postService;
    @Autowired
    PostRepo postRepo;

    @RequestMapping(value = "/get-post", method = RequestMethod.GET)
    public ResponseEntity<String> findPostByPostId(@RequestParam("pid") int post_id) {
        try {
            GetPostResponse post = postService.getPostResponseByPostId(post_id);
            System.out.println("Get post with post_id: "+post_id);
            String responseBody = JsonUtils.getStringFromObject(post);
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e){
            String responseBody = JsonUtils.getStringFromObject( new ErrorResponse(111, "post not exist", "can not find post with id: "+post_id));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/get-detail-post", method = RequestMethod.GET)
    public ResponseEntity<String> getDetailPostByPostId(@RequestParam("uid") int uid, @RequestParam("pid") int post_id) {
        try {
            GetDetailPostResponse post = postRepo.getDetailPostByUidAndPostId(uid, post_id);
            String responseBody = JsonUtils.getStringFromObject(post);
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e){
            String responseBody = JsonUtils.getStringFromObject( new ErrorResponse(111, "post not exist", "can not find post with id: "+post_id));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/get-detail-post-by-post-id", method = RequestMethod.GET)
    public ResponseEntity<String> getDetailPostByPostIds(@RequestParam("uid") int uid, @RequestParam("pid") int[] post_ids) {
        try {
            ArrayList<GetDetailPostResponse> results = new ArrayList<>();
            for(int post_id: post_ids) {
                GetDetailPostResponse post = postRepo.getDetailPostByUidAndPostId(uid, post_id);
                if(uid != 0) {
                    if(post.join == null)
                        post.join = 0;
                    if(post.save == null)
                        post.save = 0;
                    if(post.voteType == null)
                        post.voteType = "none";
                }
                results.add(post);
            }
            String responseBody = JsonUtils.getStringFromObject(results);
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e){
            String responseBody = JsonUtils.getStringFromObject( new ErrorResponse(111, "error get posts", "can not find posts"));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/get-home-posts", method = RequestMethod.GET)
    public int[] getHomePost(@RequestParam("uid") int uid, @RequestParam("sort") String sort) {
        return postService.getAllPostsForHomeByUidAndSort(uid, sort);
    }

    @RequestMapping(value = "/get-popular-posts", method = RequestMethod.GET)
    public int[] getPopularPost(@RequestParam("uid") int uid, @RequestParam("sort") String sort) {
        return  postService.getAllPostIdsForPopularByUidAndSort(uid, sort);
    }

    @RequestMapping(value = "/get-community-posts", method = RequestMethod.GET)
    public int[] getCommunityPost(@RequestParam("cid") int cid, @RequestParam("sort") String sort) {
        return postService.getAllPostsByCommunityIdAndSort(cid, sort);
    }

    @RequestMapping(value = "/get-control-posts", method = RequestMethod.GET)
    public int[] getControlPosts(@RequestParam("cid") int cid) {
        return postService.getALlPostsByCommunityIdAndNotAllow(cid);
    }

    @RequestMapping(value = "/get-search-posts", method = RequestMethod.GET)
    public int[] getPostsBySearch(@RequestParam("text") String text, @RequestParam("sort") String sort) {
        return postService.getAllPostsBySearch(text, sort);
    }

    @RequestMapping(value = "/get-posts-by-uid", method = RequestMethod.GET)
    public int[] getPostsByUid(@RequestParam("uid") int uid, @RequestParam("sort") String sort) {
        return postService.getAllPostsByUid(uid, sort);
    }

    @RequestMapping(value = "/get-posts-by-uid-not-delete-not-allow", method = RequestMethod.GET)
    public int[] getPostsByUid(@RequestParam("uid") int uid) {
        try {
            return postService.getPostIdsByUidAndNotDeleteAndNotAllow(uid);
        }
        catch (Exception e) {
            return new int[0];
        }
    }

    @RequestMapping(value = "/get-allowed-post-in-community", method = RequestMethod.GET)
    public int[] getAllowedPostIdsByCommunityIdAndAllowed(@RequestParam("cid") int community_id) {
        try {
            return postRepo.selectPostIdsByCommunityIdAndAllowed(community_id);
        }
        catch (Exception e) {
            return new int[0];
        }
    }

    @RequestMapping(value = "/get-deleted-post-in-community", method = RequestMethod.GET)
    public int[] getDeletedPostIdsByCommunityIdAndAllowed(@RequestParam("cid") int community_id) {
        try {
            return postRepo.selectPostIdsByCommunityIdAndDeleted(community_id);
        }
        catch (Exception e) {
            return new int[0];
        }
    }
}

