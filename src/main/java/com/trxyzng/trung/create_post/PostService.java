package com.trxyzng.trung.create_post;

import com.trxyzng.trung.create_post.pojo.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepo;

    public PostEntity savePostEntity(PostEntity postEntity) {
        return postRepo.save(postEntity);
    }

    public PostEntity findPostEntityByPost_id(int postId) {
        return postRepo.findPostEntityByPostId(postId);
    }

    public void updatePostEntityByPostId(int postId, String newContent) {
        postRepo.updatePostEntityByPost_id(postId, newContent);
    }

    public ArrayList<PostResponse> findAll() {
        return postRepo.findAllPostEntities();
    }
}
