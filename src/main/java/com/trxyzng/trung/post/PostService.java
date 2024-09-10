package com.trxyzng.trung.post;

import com.trxyzng.trung.authentication.shared.user.UserEntityRepo;
import com.trxyzng.trung.join_community.JoinCommunityRepo;
import com.trxyzng.trung.post.check_vote_post.CheckVotePostRepo;
import com.trxyzng.trung.post.get_post.pojo.GetDetailPostResponse;
import com.trxyzng.trung.post.get_post.pojo.GetPostResponse;
import com.trxyzng.trung.post.save_post.SavedPostRepo;
import com.trxyzng.trung.search.community.CommunityRepo;
import com.trxyzng.trung.search.user_profile.UserProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommunityRepo communityRepo;
    @Autowired
    private UserEntityRepo userEntityRepo;
    @Autowired
    private UserProfileRepo userProfileRepo;
    @Autowired
    private CheckVotePostRepo checkVotePostRepo;
    @Autowired
    private JoinCommunityRepo joinCommunityRepo;
    @Autowired
    private SavedPostRepo savedPostRepo;

    public PostEntity savePostEntity(PostEntity postEntity) {
        return postRepo.save(postEntity);
    }

    public void deletePostByPostIdAndUid(int post_id, int uid, String deleted_by) {
        //if user delete post, delete content
        if(deleted_by.equals("user"))
            this.postRepo.updateDeletedByPostIdAndUid(post_id, uid);
        //if moderator delete post, keep content
        if(deleted_by.equals("moderator"))
            this.postRepo.updateDeletedByPostIdAndUid(post_id, uid);
    }

    public PostEntity getPostEntityByPostId(int post_id) {
        return postRepo.getPostEntityByPostId(post_id).orElse(new PostEntity(0));
    }

    public void updateVoteByPostId(int postId, int vote) {
        postRepo.updateVoteByPostId(postId, vote);
    }

    public void updatePostEntityByPostId(int postId, String newContent) {
        postRepo.updatePostEntityByPostId(postId, newContent);
    }

    public void updatePostEntityByPostId(int postId, String newTitle, String newContent) {
        postRepo.updatePostEntityByPostId(postId, newTitle, newContent);
    }

    public int existsByPostId(int post_id) {
        return postRepo.existsByPostId(post_id);
    }

    public GetPostResponse getPostResponseByPostId(int post_id) {
        PostEntity postEntity = postRepo.getPostEntityByPostId(post_id).orElse(new PostEntity());
        String type = postEntity.getType();
        int uid = postEntity.getUid();
        String username = userEntityRepo.findUsernameByUid(uid);
        String username_avatar = userProfileRepo.selectAvatarFromUid(uid);
        int community_id = postEntity.getCommunity_id();
        String community_name = communityRepo.selectNameFromId(community_id);
        String community_icon = communityRepo.selectIconFromId(community_id);
        String title = postEntity.getTitle();
        String content = postEntity.getContent();
        Instant created_at = postEntity.getCreated_at();
        int vote = postEntity.getVote();
        int allow = postEntity.getAllow();
        int deleted = postEntity.getDeleted();
//        if(postEntity.getDeleted() == 1) {
//            return new GetPostResponse(post_id, type, uid, username, username_avatar, community_id, community_name, community_icon, title, content, created_at, 0,0,1);
//        }
        return new GetPostResponse(post_id, type, uid, username, username_avatar, community_id, community_name, community_icon, title, content, created_at, vote, allow, deleted);
    }

//    public GetDetailPostResponse getDetailPostResponseByPostId(int uid, int post_id) {
//        PostEntity postEntity = postRepo.getPostEntityByPostId(post_id).orElse(new PostEntity());
//        String type = postEntity.getType();
//        int uuid = postEntity.getUid();
//        String username = userEntityRepo.findUsernameByUid(postEntity.getUid());
//        String username_avatar = userProfileRepo.selectAvatarFromUid(postEntity.getUid());
//        int community_id = postEntity.getCommunity_id();
//        String community_name = communityRepo.selectNameFromId(community_id);
//        String community_icon = communityRepo.selectIconFromId(community_id);
//        String title = postEntity.getTitle();
//        String content = postEntity.getContent();
//        Instant created_at = postEntity.getCreated_at();
//        int vote = postEntity.getVote();
//        int allow = postEntity.getAllow();
//        int deleted = postEntity.getDeleted();
//        if(uid == 0) {
//            return new GetDetailPostResponse(post_id, type, uuid, username, username_avatar, community_id, community_name, community_icon, title, content, created_at, vote, allow, deleted, null, null, null, 0);
//        }
//        else {
//            String voteType = checkVotePostRepo.findVoteTypeByUidAndPostId(uid, post_id).orElse("none");
//            Integer join = joinCommunityRepo.getSubscribedStatusByUidAndCommunityId(uid, community_id).orElse(0);
//            Integer save = savedPostRepo.selectSaveByUidAndPostId(uid, post_id).orElse(0);
//            return new GetDetailPostResponse(post_id, type, uuid, username, username_avatar, community_id, community_name, community_icon, title, content, created_at, vote, allow, deleted, join, voteType, save);
//        }
//    }

    public int[] getAllPostIdsForPopularByUidAndSort(int id, String sort_type) {
        int[] post_id_arr = {};
            if(sort_type.equals("new"))
                post_id_arr = postRepo.getAllPostIdForPopularOrderByNew();
            if(sort_type.equals("hot"))
                post_id_arr = postRepo.getAllPostIdForPopularOrderByHot();
            if(sort_type.equals("top_day"))
                post_id_arr = postRepo.getAllPostIdForPopularOrderByTop(Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(1, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
            if(sort_type.equals("top_week"))
                post_id_arr = postRepo.getAllPostIdForPopularOrderByTop(Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(7, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
            if(sort_type.equals("top_month"))
                post_id_arr = postRepo.getAllPostIdForPopularOrderByTop(Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(31, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
            if(sort_type.equals("top_year"))
                post_id_arr = postRepo.getAllPostIdForPopularOrderByTop(Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(365, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
            if(sort_type.equals("top_all_time"))
                post_id_arr = postRepo.getAllPostIdForPopularOrderByTopAllTime();
            System.out.println("POPULAR NO LOGIN");
        return post_id_arr;
    }

    public int[] getAllPostsForHomeByUidAndSort(int id, String sort_type) {
        int[] post_id_arr = {};
        if(sort_type.equals("new"))
            post_id_arr = postRepo.getAllPostsForHomeWithUidByNew(id);
        if(sort_type.equals("hot"))
            post_id_arr = postRepo.getAllPostsForHomeWithUidByHot(id);
        if(sort_type.equals("top_day"))
            post_id_arr = postRepo.getAllPostsForHomeWithUidByTop(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(1, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("top_week"))
            post_id_arr = postRepo.getAllPostsForHomeWithUidByTop(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(7, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("top_month"))
            post_id_arr = postRepo.getAllPostsForHomeWithUidByTop(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(31, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("top_year"))
            post_id_arr = postRepo.getAllPostsForHomeWithUidByTop(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(365, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("top_all_time"))
            post_id_arr = postRepo.getAllPostsForHomeWithUidByTopAllTime(id);
        return  post_id_arr;
    }

    public int[] getAllPostsByCommunityIdAndSort(int id, String sort_type) {
        int[] post_id_arr = {};
        if(sort_type.equals("new"))
            post_id_arr = postRepo.getAllPostIdByCommunityIdOrderByNew(id);
        if(sort_type.equals("hot"))
            post_id_arr = postRepo.getAllPostIdByCommunityIdOrderByHot(id);
        if(sort_type.equals("top_day"))
            post_id_arr = postRepo.getAllPostIdByCommunityIdOrderByTop(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(1, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("top_week"))
            post_id_arr = postRepo.getAllPostIdByCommunityIdOrderByTop(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(7, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("top_month"))
            post_id_arr = postRepo.getAllPostIdByCommunityIdOrderByTop(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(31, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("top_year"))
            post_id_arr = postRepo.getAllPostIdByCommunityIdOrderByTop(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(365, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("top_all_time"))
            post_id_arr = postRepo.getAllPostIdByCommunityIdOrderByTopAllTime(id);
        return post_id_arr;
    }

    public int[] getALlPostsByCommunityIdAndNotAllow(int id) {
        return postRepo.getAllPostIdByCommunityIdNotAllow(id);
    }

    public int[] getAllPostsBySearch(String text, String sort_type) {
        int[] post_id_arr = {};
        if(sort_type.equals("new"))
            post_id_arr = postRepo.getAllPostsBySearchSortNew(text);
        if(sort_type.equals("top_day"))
            post_id_arr = postRepo.getAllPostsBySearchSortTop(text, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(1, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("top_week"))
            post_id_arr = postRepo.getAllPostsBySearchSortTop(text, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(7, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("top_month"))
            post_id_arr = postRepo.getAllPostsBySearchSortTop(text, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(31, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("top_year"))
            post_id_arr = postRepo.getAllPostsBySearchSortTop(text, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(365, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("top_all_time"))
            post_id_arr = postRepo.getAllPostsBySearchSortTopAllTime(text);
        return post_id_arr;
    }

    public int[] getAllPostsByUid(int uid, String sort_type) {
        int[] post_id_arr = {};
        if(sort_type.equals("new"))
            post_id_arr = postRepo.getAllPostsByUidSortNew(uid);
        if(sort_type.equals("top_day"))
            post_id_arr = postRepo.getAllPostsByUidSortTop(uid, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(1, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("top_week"))
            post_id_arr = postRepo.getAllPostsByUidSortTop(uid, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(7, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("top_month"))
            post_id_arr = postRepo.getAllPostsByUidSortTop(uid, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(31, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("top_year"))
            post_id_arr = postRepo.getAllPostsByUidSortTop(uid, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(365, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("top_all_time"))
            post_id_arr = postRepo.getAllPostsByUidSortTopAllTime(uid);
        return post_id_arr;
    }

    public int[] getPostIdsByUidAndNotDeleteAndNotAllow(int uid) {
        return postRepo.getAllPostIdByUidAndNotAllowAndNotDeleteSortNew(uid);
    }

    public GetPostResponse createGetPostResponseByPostId(int post_id) {
        PostEntity postEntity = postRepo.getPostEntityByPostId(post_id).orElse(new PostEntity());
        String type = postEntity.getType();
        int uid = postEntity.getUid();
        String username = userEntityRepo.findUsernameByUid(uid);
        String username_avatar = userProfileRepo.selectAvatarFromUid(uid);
        int community_id = postEntity.getCommunity_id();
        String community_name = communityRepo.selectNameFromId(community_id);
        String community_icon = communityRepo.selectIconFromId(community_id);
        String title = postEntity.getTitle();
        String content = postEntity.getContent();
        Instant created_at = postEntity.getCreated_at();
        int vote = postEntity.getVote();
        int allow = postEntity.getAllow();
        int deleted = postEntity.getDeleted();
        GetPostResponse p = new GetPostResponse(post_id, type, uid, username, username_avatar, community_id, community_name, community_icon, title, content, created_at, vote, allow, deleted);
        return p;
    }
}
