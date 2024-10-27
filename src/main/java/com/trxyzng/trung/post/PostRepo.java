package com.trxyzng.trung.post;

import com.trxyzng.trung.post.get_post.pojo.GetDetailPostResponse;
import com.trxyzng.trung.post.get_post.pojo.GetPostResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PostRepo extends JpaRepository<PostEntity, Integer>, PagingAndSortingRepository<PostEntity, Integer> {
    public PostEntity save(PostEntity postEntity);

    @Query("select case when count(t) > 0 then 1 else 0 end from PostEntity t where t.post_id = :post_id")
    public int existsByPostId(@Param("post_id") int post_id);

    @Query("select t.post_id from PostEntity t where t.community_id = :community_id")
    int[] selectPostIdByCommunityId(@Param("community_id") int community_id);

    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.community_id = :community_id) and (t.allow = 0) order by t.created_at desc")
    public int[] getAllPostIdByCommunityIdNotAllow(@Param("community_id") int community_id);

    @Query("select t.post_id from PostEntity t where t.community_id = :community_id and t.allow = 1 and t.deleted = 0 order by t.created_at desc")
    int[] selectPostIdsByCommunityIdAndAllowed(@Param("community_id") int community_id);

    @Query("select t.post_id from PostEntity t where t.community_id = :community_id and t.deleted = 1 and t.deleted_by = 2 order by t.created_at desc")
    int[] selectPostIdsByCommunityIdAndDeleted(@Param("community_id") int community_id);

    @Query("select t.post_id from PostEntity t where t.community_id = :community_id and t.editted = 1 order by t.created_at desc")
    int[] selectPostIdsByCommunityIdAndEditted(@Param("community_id") int community_id);

    @Modifying
    @Query("update PostEntity t set t.vote = :newVote where t.post_id = :postId")
    public void updateVoteByPostId(@Param("postId") int postId, @Param("newVote") int newVote);

    //update post content after upload image to CLoudary
    @Modifying
    @Query("update PostEntity t set t.content = :newContent where t.post_id = :postId")
    public void updatePostEntityByPostId(@Param("postId") int postId, @Param("newContent") String newContent);

    //update edit post
    @Modifying
    @Query("update PostEntity t set t.content = :newContent, t.title = :newTitle, t.allow = :allow, t.editted = :editted, t.editted_at = :editted_at where t.post_id = :postId")
    public void updatePostEntityByPostId(@Param("postId") int postId, @Param("newTitle") String newTitle, @Param("newContent") String newContent, @Param("editted") int editted, @Param("editted_at") Instant editted_at, @Param("allow") int allow);

    //update allow
    @Modifying
    @Query("update PostEntity t set t.allow = :allow, t.deleted = 0, t.deleted_by = 0, t.allowed_at = :allowed_at where t.post_id = :post_id")
    void updateAllowByPostId(@Param("post_id") int post_id, @Param("allow") int allow, @Param("allowed_at") Instant allowed_at);

    //update delete
    @Modifying
    @Query("update PostEntity t set t.allow = 0, t.deleted = 1, t.deleted_by = :deleted_by, t.deleted_at = :deleted_at where t.post_id = :postId and t.uid = :uid")
    public void updateDeletedByPostIdAndUid(@Param("postId") int postId, @Param("uid") int uid, @Param("deleted_by") int deleted_by, @Param("deleted_at") Instant deleted_at);

    @Modifying
    @Query("update PostEntity t set t.allow = 0, t.deleted = 1, t.deleted_by = 2 where t.community_id = :community_id")
    public void updateDeletedByCommunityId(@Param("community_id") int community_id);

    @Query("select t.uid from PostEntity t where t.post_id = :post_id")
    public int selectUidFromPostId(int post_id);

    @Query("select t from PostEntity t where t.post_id = :post_id")
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
    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.allow = 1) order by t.created_at desc limit 1000")
    public int[] getAllPostIdForPopularOrderByNew();

    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.allow = 1) order by t.created_at desc, t.vote desc limit 1000")
    public int[] getAllPostIdForPopularOrderByHot();

    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.allow = 1) and (t.created_at between :begin_day and :end_day) order by t.vote desc, t.created_at desc limit 1000")
    public int[] getAllPostIdForPopularOrderByTop(@Param("begin_day") Instant begin_day, @Param("end_day") Instant end_day);

    @Query("select t.post_id from PostEntity t where (t.deleted = 0) and (t.allow = 1) order by t.vote desc, t.created_at desc limit 1000")
    public int[] getAllPostIdForPopularOrderByTopAllTime();


    //get and sort for popular with uid != 0
//    @Query("select new com.trxyzng.trung.post.get_post.pojo.GetDetailPostResponse(p.post_id, p.type, p.uid, p.username, p.user) from PostEntity p left join VotePostEntity v on p.post_id = v.post_id left join JoinCommunityEntity j on p.community_id = j.community_id left join SavedPostEntity s on p.post_id = s.post_id where (v.uid = :uid) and (j.uid = :uid) and (s.uid = :uid) and (p.deleted = 0) and (p.allow = 1) order by p.created_at desc limit 100")
//    int[] getAllPostsForPopularWithUidByNew(@Param("uid") int uid);
//
//    @Query("select p.post_id from ShowPostEntity t right join PostEntity p on t.post_id = p.post_id and t.uid = :uid where (t.show is null or t.show = 0) and (p.deleted = 0) and (p.allow = 1) order by p.created_at desc, p.vote desc limit 100")
//    int[] getAllPostsForPopularWithUidByHot(@Param("uid") int uid);
//
//    @Query("select p.post_id from ShowPostEntity t right join PostEntity p on t.post_id = p.post_id and t.uid = :uid where (p.deleted = 0) and (p.allow = 1) and (p.created_at between :begin_day and :end_day) order by p.vote desc, p.created_at desc limit 100")
//    int[] getAllPostsForPopularWithUidByTop(@Param("uid") int uid, @Param("begin_day") Instant begin_day, @Param("end_day") Instant end_day);
//
//    @Query("select p.post_id from ShowPostEntity t right join PostEntity p on t.post_id = p.post_id and t.uid = :uid where (p.deleted = 0) and (p.allow = 1) order by p.vote desc, p.created_at desc limit 100")
//    int[] getAllPostsForPopularWithUidByTopAllTime(@Param("uid") int uid);


    //get and sort for home with uid != 0
    @Query("select p.post_id from PostEntity p left join JoinCommunityEntity c on (c.uid = :uid) and (c.community_id = p.community_id) where (c.subscribed = 1) and (p.deleted = 0) and (p.allow = 1) order by p.created_at desc limit 100")
    int[] getAllPostsForHomeWithUidByNew(@Param("uid") int uid);

    @Query("select p.post_id from PostEntity p left join JoinCommunityEntity c on c.uid = :uid and c.community_id = p.community_id where (c.subscribed = 1) and (p.deleted = 0) and (p.allow = 1) order by p.created_at desc, p.vote desc limit 100")
    int[] getAllPostsForHomeWithUidByHot(@Param("uid") int uid);

    @Query("select p.post_id from PostEntity p left join JoinCommunityEntity c on c.uid = :uid and c.community_id = p.community_id where (c.subscribed = 1) and (p.deleted = 0) and (p.allow = 1) and (p.created_at between :begin_day and :end_day) order by p.vote desc, p.created_at desc limit 100")
    int[] getAllPostsForHomeWithUidByTop(@Param("uid") int uid, @Param("begin_day") Instant begin_day, @Param("end_day") Instant end_day);

    @Query("select p.post_id from PostEntity p left join JoinCommunityEntity c on c.uid = :uid and c.community_id = p.community_id where (c.subscribed = 1) and (p.deleted = 0) and (p.allow = 1) order by p.vote desc, p.created_at desc limit 100")
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

    //get all post_id with uid and allow = 0
    @Query("select t.post_id from PostEntity t where (t.uid = :uid) and (t.deleted = 0) and (t.allow = 0) order by t.created_at desc")
    int[] getAllPostIdByUidAndNotAllowAndNotDeleteSortNew(@Param("uid") int uid);


    //get detail post using uid and post_id
    @Query("select new com.trxyzng.trung.post.get_post.pojo.GetDetailPostResponse (p.post_id, p.type, p.uid, up.username, up.avatar, p.community_id, c.name, c.avatar, p.title, p.content, p.created_at, p.vote, p.allow, p.deleted, j.subscribed, v.vote_type, s.saved, case when c.uid = :uid then 1 else 0 end, p.deleted_by, p.deleted_at, p.allowed_at, p.editted, p.editted_at) FROM PostEntity p left join UserProfileEntity up ON p.uid = up.uid left join CommunityEntity c ON p.community_id = c.id left join JoinCommunityEntity j on p.community_id = j.community_id and j.uid = :uid left join VotePostEntity v on p.post_id = v.post_id and v.uid = :uid left join SavedPostEntity s on p.post_id = s.post_id and s.uid = :uid where p.post_id = :post_id")
    GetDetailPostResponse getDetailPostByUidAndPostId(int uid, int post_id);
}
