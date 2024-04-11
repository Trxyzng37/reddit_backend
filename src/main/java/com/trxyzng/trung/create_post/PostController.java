package com.trxyzng.trung.create_post;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.trxyzng.trung.authentication.shared.user.services.UserEntityService;
import com.trxyzng.trung.create_post.pojo.Img;
import com.trxyzng.trung.create_post.pojo.PostResponse;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class PostController {
    @Value("${photo_storage_url}")
    private String photo_storage_url;

    @Autowired
    PostService postService;
    @Autowired
    UserEntityService userEntityService;

    private PostResponse postResponse;

    @RequestMapping(value = "/get-posts", method = RequestMethod.GET)
    public ResponseEntity<String> findAllPosts() {
        ArrayList<PostResponse> arr_result = postService.findAll();
        String responseBody = JsonUtils.getStringFromObject(arr_result);
        return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/create-post", method = RequestMethod.POST)
    public ResponseEntity<String> createPost(@RequestBody PostEntity postEntity) {
        try {
            System.out.println("Type: " + postEntity.getType());
            if (postEntity.getType().equals("editor") || postEntity.getType().equals("link")) {
                PostEntity savedPostEntity = postService.savePostEntity(postEntity);
                System.out.println("Saved post with type " + postEntity.getType() + " and post_id: " + savedPostEntity.getPost_id());
                postResponse = new PostResponse(true, savedPostEntity.getPost_id());
            }
            if (postEntity.getType().equals("img")) {
                PostEntity savedPostEntity = postService.savePostEntity(postEntity);
                int postId = savedPostEntity.getPost_id();
                System.out.println("post_id after saved: " + savedPostEntity.getPost_id());
                String imgData = postEntity.getContent();
                ObjectMapper objectMapper = new ObjectMapper();
                TypeFactory typeFactory = objectMapper.getTypeFactory();
                ArrayList<Img> imgArr = objectMapper.readValue(imgData, typeFactory.constructCollectionType(ArrayList.class, Img.class));
                for (int i=0; i<imgArr.size(); i++) {
                    String imgBase64 = imgArr.get(i).data;
                    Cloudinary cloudinary = new Cloudinary(photo_storage_url);
                    cloudinary.config.secure = true;
                    Map response = cloudinary.uploader().upload(
                            imgBase64,
                            ObjectUtils.asMap(
                                    "folder", String.valueOf(postId),
                                    "use_filename", false,
                                    "unique_filename", true,
                                    "allowed_formats", "jpeg, jpg, png"
                            )
                    );
                    String imgUrl = (String) response.get("secure_url");
                    imgArr.get(i).data = imgUrl;
                    System.out.println("Content after replace: " + imgArr.get(i).data);
                }
                String imgArrString = JsonUtils.getStringFromObject(imgArr);
                postService.updatePostEntityByPostId(postId, imgArrString);
                System.out.println("Saved post with type " + postEntity.getType() + " and post_id: " + savedPostEntity.getPost_id());
                postResponse = new PostResponse(true, savedPostEntity.getPost_id());
            }
            String responseBody = JsonUtils.getStringFromObject(postResponse);
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (IOException e) {
            postResponse = new PostResponse(false, 0);
            String responseBody = JsonUtils.getStringFromObject(postResponse);
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
