package com.trxyzng.trung.post;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
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

    //get and sort for community view
    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.allow = 1) and (t.community_id = :community_id) order by t.created_at desc limit 100")
    public int[] getAllPostIdByCommunityIdOrderByNew(@Param("community_id") int community_id);

    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.allow = 1) and (t.community_id = :community_id) order by t.created_at desc, t.vote desc limit 100")
    public int[] getAllPostIdByCommunityIdOrderByHot(@Param("community_id") int community_id);

    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.allow = 1) and (t.community_id = :community_id) and (t.created_at between :begin_day and :end_day) order by t.vote desc, t.created_at desc limit 100")
    public int[] getAllPostIdByCommunityIdOrderByTop(@Param("community_id") int community_id, @Param("begin_day") Instant begin_day, @Param("end_day") Instant end_day);

    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.allow = 1) and (t.community_id = :community_id) order by t.vote desc, t.created_at desc limit 100")
    public int[] getAllPostIdByCommunityIdOrderByTopAllTime(@Param("community_id") int community_id);

    //get and sort for popular with uid = 0
    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.allow = 1) order by t.created_at desc limit 100")
    public int[] getAllPostIdForPopularOrderByNew();

    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.allow = 1) order by t.created_at desc, t.vote desc limit 100")
    public int[] getAllPostIdForPopularOrderByHot();

    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.allow = 1) and (t.created_at between :begin_day and :end_day) order by t.vote desc, t.created_at desc limit 100")
    public int[] getAllPostIdForPopularOrderByTop(@Param("begin_day") Instant begin_day, @Param("end_day") Instant end_day);

    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.allow = 1) order by t.vote desc, t.created_at desc limit 100")
    public int[] getAllPostIdForPopularOrderByTopAllTime();

    //get and sort for popular with uid != 0
    @Query("select p.post_id from ShowPostEntity t right join PostEntity p on t.post_id = p.post_id and t.uid = :uid where (t.show is null or t.show = 0) and (p.deleted = 0) and (p.allow = 1) order by p.created_at desc limit 100")
    int[] getAllPostsForPopularWithUidByNew(@Param("uid") int uid);

    @Query("select p.post_id from ShowPostEntity t right join PostEntity p on t.post_id = p.post_id and t.uid = :uid where (t.show is null or t.show = 0) and (p.deleted = 0) and (p.allow = 1) order by p.created_at desc, p.vote desc limit 100")
    int[] getAllPostsForPopularWithUidByHot(@Param("uid") int uid);

    @Query("select p.post_id from ShowPostEntity t right join PostEntity p on t.post_id = p.post_id and t.uid = :uid where (p.deleted = 0) and (p.allow = 1) and (p.created_at between :begin_day and :end_day) order by p.vote desc, p.created_at desc limit 100")
    int[] getAllPostsForPopularWithUidByTop(@Param("uid") int uid, @Param("begin_day") Instant begin_day, @Param("end_day") Instant end_day);

    @Query("select p.post_id from ShowPostEntity t right join PostEntity p on t.post_id = p.post_id and t.uid = :uid where (p.deleted = 0) and (p.allow = 1) order by p.vote desc, p.created_at desc limit 100")
    int[] getAllPostsForPopularWithUidByTopAllTime(@Param("uid") int uid);

    //get and sort for home with uid != 0
    @Query("select p.post_id from ShowPostEntity t right join PostEntity p on t.post_id = p.post_id and t.uid = :uid left join JoinCommunityEntity c on c.uid = :uid and c.community_id = p.community_id where (t.show is null or t.show = 0) and (c.subscribed = 1) and (p.deleted = 0) and (p.allow = 1) order by p.created_at desc limit 100")
    int[] getAllPostsForHomeWithUidByNew(@Param("uid") int uid);

    @Query("select p.post_id from ShowPostEntity t right join PostEntity p on t.post_id = p.post_id and t.uid = :uid left join JoinCommunityEntity c on c.uid = :uid and c.community_id = p.community_id where (t.show is null or t.show = 0) and (c.subscribed = 1) and (p.deleted = 0) and (p.allow = 1) order by p.created_at desc, p.vote desc limit 100")
    int[] getAllPostsForHomeWithUidByHot(@Param("uid") int uid);

    @Query("select p.post_id from ShowPostEntity t right join PostEntity p on t.post_id = p.post_id and t.uid = :uid left join JoinCommunityEntity c on c.uid = :uid and c.community_id = p.community_id where (t.show is null or t.show = 0) and (c.subscribed = 1) and (p.deleted = 0) and (p.allow = 1) and (p.created_at between :begin_day and :end_day) order by p.vote desc, p.created_at desc limit 100")
    int[] getAllPostsForHomeWithUidByTop(@Param("uid") int uid, @Param("begin_day") Instant begin_day, @Param("end_day") Instant end_day);

    @Query("select p.post_id from ShowPostEntity t right join PostEntity p on t.post_id = p.post_id and t.uid = :uid left join JoinCommunityEntity c on c.uid = :uid and c.community_id = p.community_id where (t.show is null or t.show = 0) and (c.subscribed = 1) and (p.deleted = 0) and (p.allow = 1) order by p.vote desc, p.created_at desc limit 100")
    int[] getAllPostsForHomeWithUidByTopAllTime(@Param("uid") int uid);

    //get and sort for home with uid != 0 and all post has been shown
    @Query("select p.post_id from ShowPostEntity t right join PostEntity p on t.post_id = p.post_id and t.uid = :uid left join JoinCommunityEntity c on c.uid = :uid and c.community_id = p.community_id where (p.deleted = 0) and (p.allow = 1) and (c.subscribed = 1) order by p.created_at desc limit 100")
    int[] getAllShowedPostsForHomeWithUidByNew(@Param("uid") int uid);

    @Query("select p.post_id from ShowPostEntity t right join PostEntity p on t.post_id = p.post_id and t.uid = :uid left join JoinCommunityEntity c on c.uid = :uid and c.community_id = p.community_id where (p.deleted = 0) and (p.allow = 1) and (c.subscribed = 1) order by p.created_at desc, p.vote desc limit 100")
    int[] getAllShowedPostsForHomeWithUidByHot(@Param("uid") int uid);

    @Query("select p.post_id from ShowPostEntity t right join PostEntity p on t.post_id = p.post_id and t.uid = :uid left join JoinCommunityEntity c on c.uid = :uid and c.community_id = p.community_id where (p.deleted = 0) and (p.allow = 1) and (c.subscribed = 1) and (p.created_at between :begin_day and :end_day) order by p.vote desc, p.created_at desc limit 100")
    int[] getAllShowedPostsForHomeWithUidByTop(@Param("uid") int uid, @Param("begin_day") Instant begin_day, @Param("end_day") Instant end_day);

    @Query("select p.post_id from ShowPostEntity t right join PostEntity p on t.post_id = p.post_id and t.uid = :uid left join JoinCommunityEntity c on c.uid = :uid and c.community_id = p.community_id where (p.deleted = 0) and (p.allow = 1) and (c.subscribed = 1) order by p.vote desc, p.created_at desc limit 100")
    int[] getAllShowedPostsForHomeWithUidByTopAllTime(@Param("uid") int uid);

    //get posts with allow = 0
    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.community_id = :community_id) and (t.allow = 0) order by t.created_at desc limit 100")
    public int[] getAllPostIdByCommunityIdNotAllow(@Param("community_id") int community_id);

    @Modifying
    @Query("update PostEntity t set t.allow = :allow where t.post_id = :post_id  and t.deleted = 0")
    void updateAllowByPostId(@Param("post_id") int post_id, @Param("allow") int allow);

    //search posts
    @Query("select t.post_id from PostEntity t where (upper(t.title) like CONCAT('% ',upper(:text) ,' %') or upper(t.title) like CONCAT('%',upper(:text) ,' %') or upper(t.title) like CONCAT('% ',upper(:text) ,'%')) and (t.deleted = 0) and (t.allow = 1) order by t.created_at desc limit 500")
    int[] getAllPostsBySearchSortNew(@Param("text") String text);

    @Query("select t.post_id from PostEntity t where (upper(t.title) like CONCAT('% ',upper(:text) ,' %') or upper(t.title) like CONCAT('%',upper(:text) ,' %') or upper(t.title) like CONCAT('% ',upper(:text) ,'%')) and (t.deleted = 0) and (t.allow = 1) and (t.created_at between :begin_day and :end_day) order by t.vote desc, t.created_at desc limit 500")
    int[] getAllPostsBySearchSortTop(@Param("text") String text, @Param("begin_day") Instant begin_day, @Param("end_day") Instant end_day);

    @Query("select t.post_id from PostEntity t where (upper(t.title) like CONCAT('% ',upper(:text) ,' %') or upper(t.title) like CONCAT('%',upper(:text) ,' %') or upper(t.title) like CONCAT('% ',upper(:text) ,'%')) and (t.deleted = 0) and (t.allow = 1) order by t.vote desc, t.created_at desc limit 500")
    int[] getAllPostsBySearchSortTopAllTime(@Param("text") String text);

    //get posts for uid
    @Query("select t.post_id from PostEntity t where (t.uid = :uid) and (t.deleted = 0) and (t.allow = 1) order by t.created_at desc limit 500")
    int[] getAllPostsByUidSortNew(@Param("uid") int uid);

    @Query("select t.post_id from PostEntity t where (t.uid = :uid) and (t.deleted = 0) and (t.allow = 1) and (t.created_at between :begin_day and :end_day) order by t.vote desc, t.created_at desc limit 500")
    int[] getAllPostsByUidSortTop(@Param("uid") int uid, @Param("begin_day") Instant begin_day, @Param("end_day") Instant end_day);

    @Query("select t.post_id from PostEntity t where (t.uid = :uid) and (t.deleted = 0) and (t.allow = 1) order by t.vote desc, t.created_at desc limit 500")
    int[] getAllPostsByUidSortTopAllTime(@Param("uid") int uid);

    //get all posts with deleted = 0 and allow = 1
    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.allow = 1)")
    int[] getAllPostsNotDeletedAndAllow();
    //include
//    @Query("select t.post_id from PostEntity t where (upper(t.title) like CONCAT('%',upper(:text) ,'%')) and (t.deleted = 0) and (t.allow = 1) order by t.created_at desc limit 500")
//    int[] getAllPostsBySearchIncludeSortNew(@Param("text") String text);
//
//    @Query("select t.post_id from PostEntity t where (upper(t.title) like CONCAT('%',upper(:text) ,'%')) and (t.deleted = 0) and (t.allow = 1) and (t.created_at between :begin_day and :end_day) order by t.vote desc, t.created_at desc limit 500")
//    int[] getAllPostsBySearchIncludeSortTop(@Param("text") String text, @Param("begin_day") Instant begin_day, @Param("end_day") Instant end_day);
//
//    @Query("select t.post_id from PostEntity t where (upper(t.title) like CONCAT('%',upper(:text) ,'%')) and (t.deleted = 0) and (t.allow = 1) order by t.vote desc, t.created_at desc limit 500")
//    int[] getAllPostsBySearchIncludeSortTopAllTime(@Param("text") String text);
}
