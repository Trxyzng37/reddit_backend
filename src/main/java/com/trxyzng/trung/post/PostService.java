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

    public void updateVoteByPostId(int postId, int vote) {
        postRepo.updateVoteByPostId(postId, vote);
    }

    public void updatePostEntityByPostId(int postId, String newContent) {
        postRepo.updatePostEntityByPostId(postId, newContent);
    }

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
            GetPostResponse p = new GetPostResponse(post_id, type, username, community_name, "username_icon", community_icon, title, content, create_at, vote);
            results.add(p);
        }
    return results;
    }
}
