package com.trxyzng.trung.comment;

import com.trxyzng.trung.comment.pojo.*;
import com.trxyzng.trung.search.user_profile.UserProfileEntity;
import com.trxyzng.trung.search.user_profile.UserProfileRepo;
import com.trxyzng.trung.utility.EmptyObjectUtils;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    UserProfileRepo userProfileRepo;
    @RequestMapping(value = "/save-comment", method = RequestMethod.POST)
    public ResponseEntity<String> saveItem(@RequestBody CreateCommentRequest createCommentRequest) {
        System.out.println("parent id: "+ createCommentRequest.getParent_id());
        boolean parentCommentExist = createCommentRequest.getLevel() == 0 ? true : this.commentService.isCommentByIdExist(createCommentRequest.getPost_id(), createCommentRequest.getParent_id());
        if(parentCommentExist) {
            int _id = commentService.getCommentID();
            Comment comment = new Comment(
                    _id,
                    createCommentRequest.getUid(),
                    createCommentRequest.getParent_id(),
                    createCommentRequest.getContent(),
                    Instant.now().truncatedTo(ChronoUnit.MILLIS),
                    0,
                    createCommentRequest.getLevel(),
                    false
            );
            Comment c = this.commentService.saveComment(comment, createCommentRequest.getPost_id());
            CreateCommentResponse createCommentResponse = new CreateCommentResponse(true);
            String responseBody = JsonUtils.getStringFromObject(createCommentResponse);
            System.out.println("save comment with id: " + c.get_id());
            return new ResponseEntity<String>( responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("error", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

//    @RequestMapping(value = "/get-comment", method = RequestMethod.GET)
//    public ResponseEntity<String> getAllCommentByCollectionName(@RequestParam("id") int id) {
//        List<Comment> commentList = this.commentService.findAllCommentInCollection(Integer.toString(id));
//        String responseBody = JsonUtils.getStringFromObject(commentList);
//        System.out.println("response: " + responseBody);
//        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
//    }

    @RequestMapping(value = "/get-comments", method = RequestMethod.GET)
    public ResponseEntity<String> test(@RequestParam("id") int id) {
        List<Comment> resultList = new ArrayList<Comment>();
        int max_level = this.commentService.getMaxLevel(id);
        List<Comment> commentList = this.commentService.getAllCommentsInOrder(id, 0, max_level, 0, resultList);
        ArrayList<CommentResponse> results = new ArrayList<>();
        for(Comment c: commentList) {
            String username = userProfileRepo.selectUsernameFromUid(c.getUid());
            String avatar = userProfileRepo.selectAvatarFromUid(c.getUid());
            CommentResponse commentResponse = new CommentResponse(c.get_id(), c.getUid(), username, avatar, c.getParent_id(), c.getContent(), c.getLevel(), c.getCreated_at(), c.getVote(), c.isDeleted());
            results.add(commentResponse);
        }
        String responseBody = JsonUtils.getStringFromObject(results);
        System.out.println("response: " + responseBody);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/update-comment-vote", method = RequestMethod.POST)
    public ResponseEntity<String> updateCommentVote(@RequestBody VoteCommentRequest voteCommentRequest) {
        boolean updated = this.commentService.updateCommentVote(voteCommentRequest.post_id, voteCommentRequest._id, voteCommentRequest.vote);
        //update comment_karma for user
        int commentId = voteCommentRequest.get_id();
        System.out.println("_ID: "+voteCommentRequest.get_id());
        int uid = commentService.findCommentById(voteCommentRequest.post_id, commentId).getUid();
        System.out.println("uid: "+uid);
        int commentKarma = userProfileRepo.selectCommentKarmaFromUid(uid);
        System.out.println("Before comment karma: "+commentKarma);
        if(voteCommentRequest.vote_type.equals("upvote"))
            userProfileRepo.updateCommentKarmaByUid(uid, commentKarma + 1);
        if(voteCommentRequest.vote_type.equals("downvote"))
            userProfileRepo.updateCommentKarmaByUid(uid, commentKarma - 1);
        if(voteCommentRequest.vote_type.equals("none")) {
            CommentStatus commentStatus = this.commentService.findCommentStatus(voteCommentRequest.get_id(), uid);
            String previousStatus = commentStatus == null ? "none" : commentStatus.getVote_type();
            if (previousStatus.equals("upvote"))
                userProfileRepo.updateCommentKarmaByUid(uid, commentKarma - 1);
            if (previousStatus.equals("downvote"))
                userProfileRepo.updateCommentKarmaByUid(uid, commentKarma + 1);
        }
        System.out.println("after comment karma: "+userProfileRepo.selectCommentKarmaFromUid(uid));
        //
        if (updated) {
            System.out.println("update comment_id: "+voteCommentRequest._id+" with vote: "+voteCommentRequest.vote);
            String responseBody = JsonUtils.getStringFromObject(new VoteCommentResponse(updated, ""));
            this.commentService.saveOrUpdateCommentStatus(voteCommentRequest._id, voteCommentRequest.uid, voteCommentRequest.vote_type);
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        String responseBody = JsonUtils.getStringFromObject(new VoteCommentResponse(updated, "error update vote comment_id: " + voteCommentRequest._id));
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/get-comment-status", method = RequestMethod.GET)
    public ResponseEntity<String> getCommentStatus(@RequestParam("_id") int _id, @RequestParam("uid") int uid) {
        CommentStatus commentStatus = this.commentService.findCommentStatus(_id, uid);
        String voteType = commentStatus == null ? "none" : commentStatus.getVote_type();
        String responseBody = JsonUtils.getStringFromObject(new CommentStatus(_id, uid, voteType));
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/edit-comment", method = RequestMethod.POST)
    public ResponseEntity<String> editComment(@RequestBody EditCommentRequest requestBody) {
        boolean update = this.commentService.updateCommentContent(requestBody.post_id, requestBody.uid, requestBody._id, requestBody.edit_content);
        if(update) {
            Comment comment = this.commentService.findCommentById(requestBody.post_id, requestBody._id);
            String responseBody = JsonUtils.getStringFromObject(comment);
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        String responseBody = JsonUtils.getStringFromObject(new Comment());
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/delete-comment", method = RequestMethod.POST)
    public ResponseEntity<String> deleteComment(@RequestBody DeleteCommentRequest requestBody) {
        Comment comment = commentService.findCommentById(requestBody.post_id, requestBody._id);
        if(requestBody.uid == comment.getUid()) {
            boolean delete = this.commentService.deleteComment(requestBody.post_id, requestBody.uid, requestBody._id);
            if(delete) {
                String responseBody = JsonUtils.getStringFromObject(new DeleteCommentResponse(true, ""));
                return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
            }
            String responseBody = JsonUtils.getStringFromObject(new DeleteCommentResponse(false, "can not delete comment"));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        else {
            String responseBody = JsonUtils.getStringFromObject(new DeleteCommentResponse(false, "comment not exist"));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/get-comments-by-uid", method = RequestMethod.GET)
    public ResponseEntity<String> getAllCommentsByUid(@RequestParam("uid") int uid, @RequestParam("sort") String sort) {
        ArrayList<Comment> commentList = this.commentService.getCommentsByUId(uid, sort);
        String responseBody = JsonUtils.getStringFromObject(commentList);
        System.out.println("response: " + responseBody);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }
}

