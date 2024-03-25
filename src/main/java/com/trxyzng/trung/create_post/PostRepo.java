package com.trxyzng.trung.create_post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface PostRepo extends JpaRepository<PostEntity, Integer> {
    public PostEntity save(PostEntity postEntity);
    public ArrayList<PostEntity> findAll();
}
