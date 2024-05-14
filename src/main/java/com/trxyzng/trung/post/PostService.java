package com.trxyzng.trung.post;

import com.trxyzng.trung.authentication.shared.user.UserEntityRepo;
import com.trxyzng.trung.post.get_post.pojo.GetPostResponse;
import com.trxyzng.trung.search.community.CommunityRepo;
import com.trxyzng.trung.search.user_profile.UserProfileRepo;
import com.trxyzng.trung.utility.EmptyEntityUtils;
import com.trxyzng.trung.utility.EmptyObjectUtils;
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

    public PostEntity savePostEntity(PostEntity postEntity) {
        return postRepo.save(postEntity);
    }

    public void deletePostByPostIdAndUid(int post_id, int uid) {
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
        return postRepo.existsByPostId(post_id).orElse(0);
    }

    public GetPostResponse getPostResponseByPostId(int post_id) {
        PostEntity postEntity = postRepo.getPostEntityByPostId(post_id).orElse(new PostEntity());
        if(postEntity.getDeleted() == 1) {
            return new GetPostResponse(0, "", 0, "", "", 0, "", "", "", "", Instant.parse("1111-11-11T11:11:11.11Z"), 0);
        }
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
        return new GetPostResponse(post_id, type, uid, username, username_avatar, community_id, community_name, community_icon, title, content, created_at, vote);
    }

    public List<GetPostResponse> getAllPostsForPopularAndSort(String sort_type) {
        int[] post_id_arr = {};
        if(sort_type.equals("new"))
            post_id_arr = postRepo.getAllPostIdForPopularOrderByNew(Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(1, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("hot"))
            post_id_arr = postRepo.getAllPostIdForPopularOrderByHot(Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(1, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
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
        List<GetPostResponse> results = new ArrayList<GetPostResponse>();
        System.out.println("POPULAR");
        for(int i: post_id_arr)
            System.out.println(i);
        for(int i=0; i<post_id_arr.length; i++) {
            int post_id = post_id_arr[i];
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
            GetPostResponse p = new GetPostResponse(post_id, type, uid, username, username_avatar, community_id, community_name, community_icon, title, content, created_at, vote);
            results.add(p);
        }
        return results;
    }

    public List<GetPostResponse> getAllPostsByCommunityIdAndSort(int id, String sort_type) {
        int[] post_id_arr = {};
        if(sort_type.equals("new"))
            post_id_arr = postRepo.getAllPostIdByCommunityIdOrderByNew(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(1, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
        if(sort_type.equals("hot"))
            post_id_arr = postRepo.getAllPostIdByCommunityIdOrderByHot(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(1, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
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
        List<GetPostResponse> results = new ArrayList<GetPostResponse>();
        System.out.println("COMMUNITY");
        for(int i: post_id_arr)
            System.out.println(i);
        for(int i=0; i<post_id_arr.length; i++) {
            int post_id = post_id_arr[i];
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
            GetPostResponse p = new GetPostResponse(post_id, type, uid, username, username_avatar, community_id, community_name, community_icon, title, content, created_at, vote);
            results.add(p);
        }
        return results;
    }
}
