package com.trxyzng.trung.post.edit_post;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.trxyzng.trung.post.PostEntity;
import com.trxyzng.trung.post.PostService;
import com.trxyzng.trung.post.create_post.pojo.CreatePostResponse;
import com.trxyzng.trung.post.create_post.pojo.Img;
import com.trxyzng.trung.post.edit_post.pojo.EditPostRequest;
import com.trxyzng.trung.post.edit_post.pojo.EditPostResponse;
import com.trxyzng.trung.post.getpost.pojo.GetPostResponse;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class EditPostController {
    @Autowired
    PostService postService;
    @Value("${photo_storage_url}")
    private String photo_storage_url;

    @RequestMapping(value = "/edit-editor-post", method = RequestMethod.POST)
    public ResponseEntity<String> editEditorPost(@RequestBody EditPostRequest requestBody) {
        int isPostByIdxist = this.postService.existsByPostId(requestBody.getPost_id());
        if (isPostByIdxist == 1) {
            this.postService.updatePostEntityByPostId(requestBody.getPost_id(), requestBody.getTitle(), requestBody.getContent());
            String responseBody = JsonUtils.getStringFromObject(new EditPostResponse(true, ""));
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        String responseBody = JsonUtils.getStringFromObject(new EditPostResponse(false, "error edit post"));
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/edit-img-post", method = RequestMethod.POST)
    public ResponseEntity<String> editImgPost(@RequestBody EditPostRequest requestBody) throws IOException {
        int isPostByIdxist = this.postService.existsByPostId(requestBody.getPost_id());
        if (isPostByIdxist == 1) {
            String imgData = requestBody.getContent();
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            ArrayList<Img> imgArr = objectMapper.readValue(imgData, typeFactory.constructCollectionType(ArrayList.class, Img.class));
            for (int i=0; i<imgArr.size(); i++) {
                String imgBase64 = imgArr.get(i).data;
                if(imgBase64.startsWith("https://res.cloudinary.com")) {
                    System.out.println("Content without replace: " + imgArr.get(i).data);
                }
                else {
                    Cloudinary cloudinary = new Cloudinary(photo_storage_url);
                    cloudinary.config.secure = true;
                    Map response = cloudinary.uploader().upload(
                            imgBase64,
                            ObjectUtils.asMap(
                                    "folder", String.valueOf(requestBody.getPost_id()),
                                    "use_filename", false,
                                    "unique_filename", true,
                                    "allowed_formats", "jpeg, jpg, png"
                            )
                    );
                    String imgUrl = (String) response.get("secure_url");
                    imgArr.get(i).data = imgUrl;
                    System.out.println("Content after replace: " + imgArr.get(i).data);
                }
            }
            String imgArrString = JsonUtils.getStringFromObject(imgArr);
            this.postService.updatePostEntityByPostId(requestBody.getPost_id(), requestBody.getTitle(), imgArrString);
            System.out.println("update post with post_id: " + requestBody.getPost_id());
            EditPostResponse editPostResponse = new EditPostResponse(true, "");
            String responseBody = JsonUtils.getStringFromObject(editPostResponse);
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        String responseBody = JsonUtils.getStringFromObject(new EditPostResponse(false, "error edit post with post_id: "+requestBody.getPost_id()+ " , type: img"));
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
