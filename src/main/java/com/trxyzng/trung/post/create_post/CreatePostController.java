package com.trxyzng.trung.post.create_post;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.trxyzng.trung.post.PostEntity;
import com.trxyzng.trung.post.PostService;
import com.trxyzng.trung.post.create_post.pojo.CreatePostResponse;
import com.trxyzng.trung.post.create_post.pojo.Img;
import com.trxyzng.trung.search.community.CommunityRepo;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class CreatePostController {
    @Value("${photo_storage_url}")
    private String photo_storage_url;

    @Autowired
    PostService postService;

    private CreatePostResponse createPostResponse;

    @RequestMapping(value = "/create-post", method = RequestMethod.POST)
    public ResponseEntity<String> createPost(@RequestBody PostEntity postEntity) {
        try {
            System.out.println("Type: " + postEntity.getType());
            if (postEntity.getType().equals("editor") || postEntity.getType().equals("link")) {
                PostEntity savedPostEntity = postService.savePostEntity(postEntity);
                System.out.println("Saved post with type " + postEntity.getType() + " and post_id: " + savedPostEntity.getPost_id());
                createPostResponse = new CreatePostResponse(true, savedPostEntity.getPost_id());
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
                createPostResponse = new CreatePostResponse(true, savedPostEntity.getPost_id());
            }
            String responseBody = JsonUtils.getStringFromObject(createPostResponse);
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (IOException e) {
            createPostResponse = new CreatePostResponse(false, 0);
            String responseBody = JsonUtils.getStringFromObject(createPostResponse);
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (DataIntegrityViolationException e) {
            createPostResponse = new CreatePostResponse(false, 0);
            String responseBody = JsonUtils.getStringFromObject(createPostResponse);
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
    }
}
