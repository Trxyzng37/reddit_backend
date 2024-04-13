package com.trxyzng.trung.post;

import com.trxyzng.trung.post.getpost.pojo.GetPostResponse;
import com.trxyzng.trung.search.community.CommunityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommunityRepo communityRepo;

    public PostEntity savePostEntity(PostEntity postEntity) {
        return postRepo.save(postEntity);
    }

    public void updatePostEntityByPostId(int postId, String newContent) {
        postRepo.updatePostEntityByPost_id(postId, newContent);
    }

    public GetPostResponse getPostResponseFromId(int post_id) {
        String type = postRepo.selectTypeFromPostId(post_id);
        String community_name = postRepo.selectCommunityNameFromPostId(post_id);
        String community_icon = communityRepo.selectIconFromName(community_name);
        String username = postRepo.selectUserNameFromPostId(post_id);
        String title = postRepo.selectTitleFromPostId(post_id);
        String content = postRepo.selectContentFromPostId(post_id);
        int vote = postRepo.selectVoteFromPostId(post_id);
        Instant create_at = postRepo.selectCreatedAtFromPostId(post_id);
        return new GetPostResponse(post_id, type, username, community_name, "username_icon", community_icon, title, content, create_at, vote);
    }
}
