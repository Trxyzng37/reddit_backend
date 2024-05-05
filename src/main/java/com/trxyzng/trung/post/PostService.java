package com.trxyzng.trung.post;

import com.trxyzng.trung.authentication.shared.user.UserEntityRepo;
import com.trxyzng.trung.authentication.shared.user.services.UserEntityService;
import com.trxyzng.trung.post.getpost.pojo.GetPostResponse;
import com.trxyzng.trung.search.community.CommunityRepo;
import com.trxyzng.trung.search.user_profile.UserProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

//    public void deletePostByPostIdAndUsername(int post_id, int uid) {
//        this.postRepo.updateDeletedByPostIdAndUsername(post_id, uid);
//    }

    public PostEntity getPostEntityByPostId(int post_id) {
        return postRepo.getPostEntityByPostId(post_id);
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

    public GetPostResponse getPostRespnseByPostId(int post_id) {
        PostEntity postEntity = postRepo.getPostEntityByPostId(post_id);
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

    public List<GetPostResponse> getAllPosts() {
        int[] post_id_arr = postRepo.selectPostIdFromPostId();
        List<GetPostResponse> results = new ArrayList<GetPostResponse>();
        for(int i=0; i<post_id_arr.length; i++) {
            int post_id = post_id_arr[i];
            PostEntity postEntity = postRepo.getPostEntityByPostId(post_id);
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
