package com.trxyzng.trung.post.check_vote_post;

import com.trxyzng.trung.authentication.shared.user.services.UserEntityService;
import com.trxyzng.trung.post.PostService;
import com.trxyzng.trung.post.check_vote_post.pojo.CheckVotePostResponse;
import com.trxyzng.trung.post.vote_post.pojo.VotePostRequest;
import com.trxyzng.trung.post.vote_post.pojo.VotePostResponse;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckVotePostController {
    @Autowired
    CheckVotePostService checkVotePostService;
    @Autowired
    UserEntityService userEntityService;
    @RequestMapping(value = "/check-vote-post", method = RequestMethod.GET)
    public ResponseEntity<String> votePost(
            @RequestParam("uid") int uid,
            @RequestParam("postId") int postId) {
        try {
            System.out.println("uid: "+uid);
            if (uid == 0) {
                String responseBody = JsonUtils.getStringFromObject(new CheckVotePostResponse("none"));
                return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
            }
            else {
                String voteType = checkVotePostService.findVoteTypeByUidAndPostId(uid, postId);
                System.out.println("voteType: "+voteType);
                if (voteType.isEmpty()) {
                    String responseBody = JsonUtils.getStringFromObject(new CheckVotePostResponse("none"));
                    return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
                }
                else {
                    String responseBody = JsonUtils.getStringFromObject(new CheckVotePostResponse(voteType));
                    return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
                }
            }
        }
        catch (Exception e) {
            String responseBody = JsonUtils.getStringFromObject(new CheckVotePostResponse("none"));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
