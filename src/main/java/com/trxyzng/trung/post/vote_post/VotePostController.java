package com.trxyzng.trung.post.vote_post;

import com.trxyzng.trung.authentication.shared.user.services.UserEntityService;
import com.trxyzng.trung.post.PostService;
import com.trxyzng.trung.post.check_vote_post.CheckVotePostService;
import com.trxyzng.trung.post.check_vote_post.VotePostEntity;
import com.trxyzng.trung.post.vote_post.pojo.VotePostRequest;
import com.trxyzng.trung.post.vote_post.pojo.VotePostResponse;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VotePostController {
    @Autowired
    PostService postService;
    @Autowired
    CheckVotePostService checkVotePostService;
    @Autowired
    UserEntityService userEntityService;

    @RequestMapping(value = "/vote-post", method = RequestMethod.POST)
    public ResponseEntity<String> votePost(@RequestBody VotePostRequest votePostRequest) {
        try {
            int postId = votePostRequest.getPost_id();
            int vote = votePostRequest.getVote();
            String voteType = votePostRequest.getType();
            int uid = votePostRequest.getUid();
            if (uid == 0) {
                String responseBody = JsonUtils.getStringFromObject(new VotePostResponse(postId, false));
                return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
            }
            else {
                postService.updateVoteByPostId(postId, vote);
                System.out.println("update post_id: "+postId+" with vote: "+vote);
                String voteTypeExist = checkVotePostService.findVoteTypeByUidAndPostId(uid, postId);
                if (voteTypeExist.isEmpty()) {
                    System.out.println("Save new vote_info with vote_type: " + voteType);
                    System.out.println("Save to vote_info with uid: "+uid+" post_id: "+postId+" vote_type: "+voteType);
                    VotePostEntity checkVotePostEntity = checkVotePostService.saveCheckVotePostEntity(uid, postId, voteType);
                }
                else {
                    checkVotePostService.updateVoteTypeByUidAndPostId(uid, postId, voteType);
                    System.out.println("Update vote_info with uid: "+uid+" post_id: "+postId+" vote_type: "+voteType);
                }
                String responseBody = JsonUtils.getStringFromObject(new VotePostResponse(postId, true));
                return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
            }
        }
        catch (Exception e) {
            int postId = votePostRequest.getPost_id();
            System.out.println(e.getMessage());
            String responseBody = JsonUtils.getStringFromObject(new VotePostResponse(postId, false));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
