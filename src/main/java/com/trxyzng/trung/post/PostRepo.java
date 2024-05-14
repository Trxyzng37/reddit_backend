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

    @Query("select 1 from PostEntity t where t.post_id = :post_id and t.deleted = 0")
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

    @Modifying
    @Query("update PostEntity t set t.deleted = 1 where t.post_id = :postId and t.uid = :uid")
    public void updateDeletedByPostIdAndUid(@Param("postId") int postId, @Param("uid") int uid);

    @Query("select t.post_id from PostEntity t where t.deleted = 0")
    public int[] selectPostIdFromPostId();

    @Query("select t from PostEntity t where t.post_id = :post_id and t.deleted = 0")
    Optional<PostEntity> getPostEntityByPostId(@Param("post_id") int post_id);

    //get and sort for community
    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.community_id = :community_id) and (t.created_at between :begin_day and :end_day) order by t.created_at desc limit 100")
    public int[] getAllPostIdByCommunityIdOrderByNew(@Param("community_id") int community_id, @Param("begin_day") Instant begin_day, @Param("end_day") Instant end_day);

    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.community_id = :community_id) and (t.created_at between :begin_day and :end_day) order by t.vote desc, t.created_at desc limit 100")
    public int[] getAllPostIdByCommunityIdOrderByHot(@Param("community_id") int community_id, @Param("begin_day") Instant begin_day, @Param("end_day") Instant end_day);

    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.community_id = :community_id) and (t.created_at between :begin_day and :end_day) order by t.vote desc, t.created_at desc limit 100")
    public int[] getAllPostIdByCommunityIdOrderByTop(@Param("community_id") int community_id, @Param("begin_day") Instant begin_day, @Param("end_day") Instant end_day);

    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.community_id = :community_id) order by t.vote desc, t.created_at desc limit 100")
    public int[] getAllPostIdByCommunityIdOrderByTopAllTime(@Param("community_id") int community_id);

    //get and sort for popular
    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.created_at between :begin_day and :end_day) order by t.created_at desc limit 100")
    public int[] getAllPostIdForPopularOrderByNew(@Param("begin_day") Instant begin_day, @Param("end_day") Instant end_day);

    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.created_at between :begin_day and :end_day) order by t.vote desc, t.created_at desc limit 100")
    public int[] getAllPostIdForPopularOrderByHot(@Param("begin_day") Instant begin_day, @Param("end_day") Instant end_day);

    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.created_at between :begin_day and :end_day) order by t.vote desc, t.created_at desc limit 100")
    public int[] getAllPostIdForPopularOrderByTop(@Param("begin_day") Instant begin_day, @Param("end_day") Instant end_day);

    @Query("select t.post_id from PostEntity t where (t.deleted = 0) order by t.vote desc, t.created_at desc limit 100")
    public int[] getAllPostIdForPopularOrderByTopAllTime();
}
