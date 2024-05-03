package com.trxyzng.trung.post;

import com.trxyzng.trung.post.getpost.pojo.GetPostResponse;
import com.trxyzng.trung.search.community.CommunityRepo;
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

    public PostEntity savePostEntity(PostEntity postEntity) {
        return postRepo.save(postEntity);
    }

    public void deletePostByPostIdAndUsername(int post_id, String username) {
        this.postRepo.deletePostEntityByPostId(post_id, username);
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

    public GetPostResponse getPostByPostId(int post_id) {
        String type = postRepo.selectTypeFromPostId(post_id);
        String community_name = postRepo.selectCommunityNameFromPostId(post_id);
        String community_icon = communityRepo.selectIconFromName(community_name);
        String username = postRepo.selectUserNameFromPostId(post_id);
        String title = postRepo.selectTitleFromPostId(post_id);
        String content = postRepo.selectContentFromPostId(post_id);
        int vote = postRepo.selectVoteFromPostId(post_id);
        Instant create_at = postRepo.selectCreatedAtFromPostId(post_id);
        return new GetPostResponse(post_id, type, username, "username_icon_url", community_name, community_icon, title, content, create_at, vote);
    }

    //missing user_icon
    public List<GetPostResponse> getPostResponseFromId(int ppost_id) {
        int[] post_id_arr = postRepo.selectPostIdFromPostId();
        List<GetPostResponse> results = new ArrayList<GetPostResponse>();
        for(int i=0; i<post_id_arr.length; i++) {
            int post_id = post_id_arr[i];
            String type = postRepo.selectTypeFromPostId(post_id);
            String community_name = postRepo.selectCommunityNameFromPostId(post_id);
            String community_icon = communityRepo.selectIconFromName(community_name);
            String username = postRepo.selectUserNameFromPostId(post_id);
            String title = postRepo.selectTitleFromPostId(post_id);
            String content = postRepo.selectContentFromPostId(post_id);
            int vote = postRepo.selectVoteFromPostId(post_id);
            Instant create_at = postRepo.selectCreatedAtFromPostId(post_id);
            GetPostResponse p = new GetPostResponse(post_id, type, username, "username_icon_url", community_name, community_icon, title, content, create_at, vote);
            results.add(p);
        }
    return results;
    }
}
