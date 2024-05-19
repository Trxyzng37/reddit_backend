package com.trxyzng.trung.post.allow_post;

import com.trxyzng.trung.post.PostRepo;
import com.trxyzng.trung.post.get_post.pojo.GetPostResponse;
import com.trxyzng.trung.utility.DefaultResponse;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AllowPostController {
    @Autowired
    PostRepo repo;

    @RequestMapping(value = "/set-allow-post", method = RequestMethod.POST)
    public ResponseEntity<String> setAllowPost(@RequestBody AllowPostRequest body) {
        try {
            repo.updateAllowByPostId(body.getPost_id(), body.getAllow());
            String responseBody = JsonUtils.getStringFromObject(new DefaultResponse(0,""));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e) {
            String responseBody = JsonUtils.getStringFromObject(new DefaultResponse(1,"error set allow for post"));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
    }
}
