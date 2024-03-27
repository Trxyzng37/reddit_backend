package com.trxyzng.trung.create_post;

import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class PostController {
    @Autowired
    PostService postService;
    @RequestMapping(value = "/get-posts", method = RequestMethod.GET)
    public ResponseEntity<String> findAllPosts() {
        ArrayList<PostResponse> arr_result = postService.findAll();
        String responseBody = JsonUtils.getStringFromObject(arr_result);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }
}
