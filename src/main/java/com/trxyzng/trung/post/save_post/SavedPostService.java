package com.trxyzng.trung.post.save_post;

import com.trxyzng.trung.authentication.shared.user.UserEntityRepo;
import com.trxyzng.trung.post.PostEntity;
import com.trxyzng.trung.post.PostRepo;
import com.trxyzng.trung.post.get_post.pojo.GetPostResponse;
import com.trxyzng.trung.search.community.CommunityRepo;
import com.trxyzng.trung.search.user_profile.UserProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class SavedPostService {
    @Autowired
    SavedPostRepo savedPostRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommunityRepo communityRepo;
    @Autowired
    private UserEntityRepo userEntityRepo;
    @Autowired
    private UserProfileRepo userProfileRepo;

    public int[] getAllPostResponsesByUid(int uid) {
        return savedPostRepo.getAllPostIdByUid(uid);
    }

    public void saveOrUpdateSavedPost(int uid, int post_id, int saved) {
        int exist = savedPostRepo.existsByPostIdAndUid(post_id, uid);
        if(exist == 0) {
            savedPostRepo.save(new SavedPostEntity(uid, post_id, saved, Instant.now().truncatedTo(ChronoUnit.MILLIS)));
        }
        else {
            savedPostRepo.updateByUidAndPostId(uid, post_id, saved, Instant.now().truncatedTo(ChronoUnit.MILLIS));
        }
    }

    public int getSaveStatusByUidAndPostId(int uid, int post_id) {
        int exist = savedPostRepo.existsByPostIdAndUid(post_id, uid);
        if(exist == 0)
            return 0;
        return savedPostRepo.selectSavedByUidAndPostId(uid, post_id);
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
