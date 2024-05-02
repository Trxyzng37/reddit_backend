package com.trxyzng.trung.post;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
@Transactional
public interface PostRepo extends JpaRepository<PostEntity, Integer> {
    public PostEntity save(PostEntity postEntity);

    @Query("select 1 from PostEntity t where t.post_id = :post_id")
    public Optional<Integer> existsByPostId(@Param("post_id") int post_id);

    @Modifying
    @Query("update PostEntity t set t.content = :newContent where t.post_id = :postId")
    public void updatePostEntityByPostId(@Param("postId") int postId, @Param("newContent") String newContent);

    @Modifying
    @Query("update PostEntity t set t.content = :newContent, t.title = :newTitle where t.post_id = :postId")
    public void updatePostEntityByPostId(@Param("postId") int postId, @Param("newTitle") String newTitle, @Param("newContent") String newContent);

    @Modifying
    @Query("update PostEntity t set t.vote = :newVote where t.post_id = :postId")
    public void updateVoteByPostId(@Param("postId") int postId, @Param("newVote") int newVote);

    @Query("select t.post_id from PostEntity t")
    public int[] selectPostIdFromPostId();
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
