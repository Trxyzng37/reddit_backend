package com.trxyzng.trung.post.show_post;

import com.trxyzng.trung.post.get_post.pojo.GetPostResponse;
import com.trxyzng.trung.post.show_post.pojo.ShowPostRequest;
import com.trxyzng.trung.post.show_post.pojo.ShowPostResponse;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ShowPostController {
    @Autowired
    ShowPostService showPostService;

    @RequestMapping(value = "/show-post", method = RequestMethod.POST)
    public ResponseEntity<String> setShowPost(@RequestBody ShowPostRequest body) {
        try {
            showPostService.saveOrUpdateShowPostEntity(body.getUid(), body.getPost_id(), body.getShow());
            String responseBody = JsonUtils.getStringFromObject(new ShowPostResponse(0));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            String responseBody = JsonUtils.getStringFromObject(new ShowPostResponse(1));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
