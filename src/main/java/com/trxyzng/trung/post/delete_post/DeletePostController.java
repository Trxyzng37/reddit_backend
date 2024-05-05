package com.trxyzng.trung.post.delete_post;

import com.trxyzng.trung.post.PostEntity;
import com.trxyzng.trung.post.PostService;
import com.trxyzng.trung.post.delete_post.pojo.DeletePostRequest;
import com.trxyzng.trung.post.delete_post.pojo.DeletePostResponse;
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
public class DeletePostController {

    @Autowired
    PostService postService;

    @RequestMapping(value = "/delete-post", method = RequestMethod.POST)
    public ResponseEntity<String> deletePost(@RequestBody DeletePostRequest requestBody) {
        this.postService.deletePostByPostIdAndUid(requestBody.getPost_id(), requestBody.getUid());
        PostEntity postEntity = this.postService.getPostEntityByPostId(requestBody.getPost_id());
        if (postEntity.getUid() == 0) {
            String responseBody = JsonUtils.getStringFromObject(new DeletePostResponse(true, ""));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        String responseBody = JsonUtils.getStringFromObject(new DeletePostResponse(false, "error delete post"+requestBody.getPost_id()));
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
