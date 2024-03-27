package com.trxyzng.trung.create_post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface PostRepo extends JpaRepository<PostEntity, Integer> {
    public PostEntity save(PostEntity postEntity);
    @Query("select t from PostEntity t")
    public ArrayList<PostResponse> findAllPostEntities();
}
