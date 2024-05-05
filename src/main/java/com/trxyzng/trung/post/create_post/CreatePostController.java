package com.trxyzng.trung.post.create_post;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.trxyzng.trung.authentication.shared.user.services.UserEntityService;
import com.trxyzng.trung.post.PostEntity;
import com.trxyzng.trung.post.PostService;
import com.trxyzng.trung.post.create_post.pojo.CreatePostRequest;
import com.trxyzng.trung.post.create_post.pojo.CreatePostResponse;
import com.trxyzng.trung.post.create_post.pojo.Img;
import com.trxyzng.trung.post.create_post.pojo.Index;
import com.trxyzng.trung.post.getpost.pojo.LinkPostData;
import com.trxyzng.trung.search.community.CommunityRepo;
import com.trxyzng.trung.search.community.CommunityService;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class CreatePostController {
    @Value("${photo_storage_url}")
    private String photo_storage_url;

    @Autowired
    PostService postService;

    @Autowired
    UserEntityService userEntityService;

    @Autowired
    CommunityService communityService;

    private CreatePostResponse createPostResponse;

    @RequestMapping(value = "/create-post", method = RequestMethod.POST)
    public ResponseEntity<String> createPost(@RequestBody CreatePostRequest createPostRequest) {
        try {
            boolean isUserExist = this.userEntityService.isUserEntityByUidExist(createPostRequest.getUid());
            boolean isCommunityIdExist = communityService.isCommunityEntityByIdExist(createPostRequest.getCommunity_id());
            if (isUserExist && isCommunityIdExist) {
                System.out.println("Type: " + createPostRequest.getType());
                if (createPostRequest.getType().equals("editor")) {
                    PostEntity postEntity = new PostEntity(
                            createPostRequest.getType(),
                            createPostRequest.getUid(),
                            createPostRequest.getCommunity_id(),
                            createPostRequest.getTitle(),
                            createPostRequest.getContent(),
                            Instant.now().truncatedTo(ChronoUnit.MILLIS)
                    );
                    PostEntity savedPostEntity = postService.savePostEntity(postEntity);
                    int postId = savedPostEntity.getPost_id();
                    System.out.println("post_id after saved: " + savedPostEntity.getPost_id());
                    String regex = "src=\"([^\"]*)\"";
                    String content = postEntity.getContent();
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(content);
                    ArrayList<String> imgWithURL = new ArrayList<String>();
                    ArrayList<Index> index = new ArrayList<Index>();
                    while (matcher.find()) {
                        String base64_img = matcher.group(1);
                        int startIndex = matcher.start();
                        int endIndex = matcher.end();
                        index.add(new Index(startIndex, endIndex));
                        System.out.println("start(): " + matcher.start());
                        System.out.println("end(): " + matcher.end());
                        Cloudinary cloudinary = new Cloudinary(photo_storage_url);
                        cloudinary.config.secure = true;
                        Map response = cloudinary.uploader().upload(
                                base64_img,
                                ObjectUtils.asMap(
                                        "folder", String.valueOf(postId),
                                        "use_filename", false,
                                        "unique_filename", true,
                                        "allowed_formats", "jpeg, jpg, png"
                                )
                        );
                        String imgUrl = (String) response.get("secure_url");
                        imgWithURL.add(imgUrl);
                    }
                    System.out.println("Found " + imgWithURL.size() + " img src");
                    for (int i = 0; i < imgWithURL.size(); i++) {
                        System.out.println("url: " + imgWithURL.get(i));
                    }
                    String newContent = "";
                    if (index.size() == 1) {
                        newContent += content.substring(0, index.get(0).start + 5) + imgWithURL.get(0) + content.substring(index.get(0).end - 1, content.length());
                    } else if (index.size() > 1) {
                        for (int i = 0; i < index.size(); i++) {
                            if (i == 0) {
                                newContent += content.substring(0, index.get(i).start + 5) + imgWithURL.get(i);
//                        System.out.println("newContent: " + newContent);
                            } else if (i == index.size() - 1) {
                                newContent += content.substring(index.get(i - 1).end - 1, index.get(i).start + 5) + imgWithURL.get(i) + content.substring(index.get(i).end - 1, content.length());
//                        System.out.println("newContent: " + newContent);
                            } else {
                                newContent += content.substring(index.get(i - 1).end - 1, index.get(i).start + 5) + imgWithURL.get(i);
//                        System.out.println("newContent: " + newContent);
                            }
                        }
                    } else {
                        newContent = content;
                    }
                    System.out.println("Content after replace: " + newContent);
                    postService.updatePostEntityByPostId(postId, newContent);
                    System.out.println("Update post with type " + postEntity.getType() + " and post_id: " + savedPostEntity.getPost_id());
                    createPostResponse = new CreatePostResponse(true, savedPostEntity.getPost_id());
                }
                if (createPostRequest.getType().equals("img")) {
                    PostEntity postEntity = new PostEntity(
                            createPostRequest.getType(),
                            createPostRequest.getUid(),
                            createPostRequest.getCommunity_id(),
                            createPostRequest.getTitle(),
                            createPostRequest.getContent(),
                            Instant.now().truncatedTo(ChronoUnit.MILLIS)
                    );
                    PostEntity savedPostEntity = postService.savePostEntity(postEntity);
                    int postId = savedPostEntity.getPost_id();
                    System.out.println("post_id after saved: " + savedPostEntity.getPost_id());
                    String imgData = postEntity.getContent();
                    ObjectMapper objectMapper = new ObjectMapper();
                    TypeFactory typeFactory = objectMapper.getTypeFactory();
                    ArrayList<Img> imgArr = objectMapper.readValue(imgData, typeFactory.constructCollectionType(ArrayList.class, Img.class));
                    for (int i = 0; i < imgArr.size(); i++) {
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
                if (createPostRequest.getType().equals("link")) {
                    PostEntity postEntity = new PostEntity(
                            createPostRequest.getType(),
                            createPostRequest.getUid(),
                            createPostRequest.getCommunity_id(),
                            createPostRequest.getTitle(),
                            createPostRequest.getContent(),
                            Instant.now().truncatedTo(ChronoUnit.MILLIS)
                    );
                    System.out.println(postEntity.getContent());
                    URL oracle = new URL(postEntity.getContent());
                    BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
                    String htmlContent = "";
                    String l;
                    while ((l = in.readLine()) != null) {
                        htmlContent += l;
//                    System.out.println(l);
                    }
                    in.close();
                    String titleRegex = "<meta property=\"og:title\" content=\"([^\"]*)\"";
                    Pattern titlePattern = Pattern.compile(titleRegex);
                    Matcher titleMatcher = titlePattern.matcher(htmlContent);
                    String title = "";
                    if (titleMatcher.find()) {
                        title = titleMatcher.group(1);
                    }
                    System.out.println("Title:" + title);
                    String imageRegex = "<meta property=\"og:image\" content=\"([^\"]*)\"";
                    Pattern imagePattern = Pattern.compile(imageRegex);
                    Matcher imageMatcher = imagePattern.matcher(htmlContent);
                    String image = "";
                    if (imageMatcher.find()) {
                        image = imageMatcher.group(1);
                    }
                    System.out.println("image url:" + image);
                    String urlRegex = "<meta property=\"og:url\" content=\"([^\"]*)\"";
                    Pattern urlPattern = Pattern.compile(urlRegex);
                    Matcher urlMatcher = urlPattern.matcher(htmlContent);
                    String url = "";
                    if (urlMatcher.find()) {
                        url = urlMatcher.group(1);
                    }
                    System.out.println("url:" + url);
                    LinkPostData p = new LinkPostData(postEntity.getContent(), title, image, url);
                    postEntity.setContent(JsonUtils.getStringFromObject(p));
                    PostEntity savedPostEntity = postService.savePostEntity(postEntity);
                    createPostResponse = new CreatePostResponse(true, savedPostEntity.getPost_id());
                    System.out.println("Saved post with type " + savedPostEntity.getType() + " and post_id: " + savedPostEntity.getPost_id());
                    System.out.println(p);
                }
                String responseBody = JsonUtils.getStringFromObject(createPostResponse);
                return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
            }
            else {
                System.out.println("Uid or community_id not exist in database");
                createPostResponse = new CreatePostResponse(false, 0);
                String responseBody = JsonUtils.getStringFromObject(createPostResponse);
                return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
            }
        }
        catch (IOException e) {
            createPostResponse = new CreatePostResponse(false, 0);
            String responseBody = JsonUtils.getStringFromObject(createPostResponse);
            System.out.println(e);
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch (DataIntegrityViolationException e) {
            System.out.println(e);
            createPostResponse = new CreatePostResponse(false, 0);
            String responseBody = JsonUtils.getStringFromObject(createPostResponse);
            return new ResponseEntity<String>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
    }
}
