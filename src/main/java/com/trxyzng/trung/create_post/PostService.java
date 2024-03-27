package com.trxyzng.trung.create_post;

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

    public ArrayList<PostResponse> findAll() {
        return postRepo.findAllPostEntities();
    }
}
