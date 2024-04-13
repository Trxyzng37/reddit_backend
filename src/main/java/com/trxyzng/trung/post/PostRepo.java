package com.trxyzng.trung.post;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
@Transactional
public interface PostRepo extends JpaRepository<PostEntity, Integer> {
    public PostEntity save(PostEntity postEntity);

    @Modifying
    @Query("update PostEntity t set t.content = :newContent where t.post_id = :postId")
    public void updatePostEntityByPost_id(@Param("postId") int postId, @Param("newContent") String newContent);

    @Query("select t.type from PostEntity t where t.post_id = :id")
    public String selectTypeFromPostId(int id);
    @Query("select t.community_name from PostEntity t where t.post_id = :id")
    public String selectCommunityNameFromPostId(int id);
    @Query("select t.username from PostEntity t where t.post_id = :id")
    public String selectUserNameFromPostId(int id);
    @Query("select t.title from PostEntity t where t.post_id = :id")
    public String selectTitleFromPostId(int id);
    @Query("select t.content from PostEntity t where t.post_id = :id")
    public String selectContentFromPostId(int id);
    @Query("select t.created_at from PostEntity t where t.post_id = :id")
    public Instant selectCreatedAtFromPostId(int id);
    @Query("select t.vote from PostEntity t where t.post_id = :id")
    public int selectVoteFromPostId(int id);
}
