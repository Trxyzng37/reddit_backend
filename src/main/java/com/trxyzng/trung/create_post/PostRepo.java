package com.trxyzng.trung.create_post;

import com.trxyzng.trung.create_post.pojo.PostResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Transactional
public interface PostRepo extends JpaRepository<PostEntity, Integer> {
    public PostEntity save(PostEntity postEntity);
    @Query("select t from PostEntity t")
    public ArrayList<PostResponse> findAllPostEntities();

    @Query("select t from PostEntity t where t.post_id = :postId")
    public PostEntity findPostEntityByPostId(@Param("postId") int postId);

    @Modifying
    @Query("update PostEntity t set t.content = :newContent where t.post_id = :postId")
    public void updatePostEntityByPost_id(@Param("postId") int postId, @Param("newContent") String newContent);
}
