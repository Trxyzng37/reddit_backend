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
            return new GetPostResponse(0, "", 0, "", "", 0, "", "", "", "", Instant.parse("1111-11-11T11:11:11.11Z"), 0,0,0);
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
        int allow = postEntity.getAllow();
        int deleted = postEntity.getDeleted();
        return new GetPostResponse(post_id, type, uid, username, username_avatar, community_id, community_name, community_icon, title, content, created_at, vote, allow, deleted);
    }

    public List<GetPostResponse> getAllPostsForPopularByUidAndSort(int id, String sort_type) {
        int[] post_id_arr = {};
        List<GetPostResponse> results = new ArrayList<GetPostResponse>();
        if(id == 0) {
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
            for(int i: post_id_arr)
                System.out.println(i);
            for(int i=0; i<post_id_arr.length; i++) {
                int post_id = post_id_arr[i];
                GetPostResponse p = createGetPostResponseByPostId(post_id);
                results.add(p);
            }
        }
        else {
            if(sort_type.equals("new"))
                post_id_arr = postRepo.getAllPostsForPopularWithUidByNew(id);
            if(sort_type.equals("hot"))
                post_id_arr = postRepo.getAllPostsForPopularWithUidByHot(id);
            if(sort_type.equals("top_day"))
                post_id_arr = postRepo.getAllPostsForPopularWithUidByTop(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(1, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
            if(sort_type.equals("top_week"))
                post_id_arr = postRepo.getAllPostsForPopularWithUidByTop(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(7, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
            if(sort_type.equals("top_month"))
                post_id_arr = postRepo.getAllPostsForPopularWithUidByTop(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(31, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
            if(sort_type.equals("top_year"))
                post_id_arr = postRepo.getAllPostsForPopularWithUidByTop(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(365, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
            if(sort_type.equals("top_all_time"))
                post_id_arr = postRepo.getAllPostsForPopularWithUidByTopAllTime(id);
            System.out.println("POPULAR");
            for(int i: post_id_arr)
                System.out.println(i);
            for(int i=0; i<post_id_arr.length; i++) {
                int post_id = post_id_arr[i];
                GetPostResponse p = createGetPostResponseByPostId(post_id);
                results.add(p);
            }
        }
        //if user has show all posts, then find posts like when uid = 0
        if (results.size() == 0) {
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
            System.out.println("POPULAR SHOWED");
            for(int i: post_id_arr)
                System.out.println(i);
            for(int i=0; i<post_id_arr.length; i++) {
                int post_id = post_id_arr[i];
                GetPostResponse p = createGetPostResponseByPostId(post_id);
                results.add(p);
            }
        }
        return results;
    }

    public List<GetPostResponse> getAllPostsForHomeByUidAndSort(int id, String sort_type) {
        int[] post_id_arr = {};
        List<GetPostResponse> results = new ArrayList<GetPostResponse>();
        //id uid=0, show like popular
        if(id == 0) {
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
            System.out.println("HOME NO LOGIN");
            for(int i: post_id_arr)
                System.out.println(i);
            for(int i=0; i<post_id_arr.length; i++) {
                int post_id = post_id_arr[i];
                GetPostResponse p = createGetPostResponseByPostId(post_id);
                results.add(p);
            }
        }
        else {
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
            System.out.println("HOME");
            for(int i: post_id_arr)
                System.out.println(i);
            for(int i=0; i<post_id_arr.length; i++) {
                int post_id = post_id_arr[i];
                GetPostResponse p = createGetPostResponseByPostId(post_id);
                results.add(p);
            }
            //if all post has been shown, show all them
            if(results.size() == 0) {
                if(sort_type.equals("new"))
                    post_id_arr = postRepo.getAllShowedPostsForHomeWithUidByNew(id);
                if(sort_type.equals("hot"))
                    post_id_arr = postRepo.getAllShowedPostsForHomeWithUidByHot(id);
                if(sort_type.equals("top_day"))
                    post_id_arr = postRepo.getAllShowedPostsForHomeWithUidByTop(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(1, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
                if(sort_type.equals("top_week"))
                    post_id_arr = postRepo.getAllShowedPostsForHomeWithUidByTop(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(7, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
                if(sort_type.equals("top_month"))
                    post_id_arr = postRepo.getAllShowedPostsForHomeWithUidByTop(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(31, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
                if(sort_type.equals("top_year"))
                    post_id_arr = postRepo.getAllShowedPostsForHomeWithUidByTop(id, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(365, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
                if(sort_type.equals("top_all_time"))
                    post_id_arr = postRepo.getAllShowedPostsForHomeWithUidByTopAllTime(id);
                System.out.println("HOME SHOWED");
                for(int i: post_id_arr)
                    System.out.println(i);
                for(int i=0; i<post_id_arr.length; i++) {
                    int post_id = post_id_arr[i];
                    GetPostResponse p = createGetPostResponseByPostId(post_id);
                    results.add(p);
                }
            }
        }
        return  results;
    }

    public List<GetPostResponse> getAllPostsByCommunityIdAndSort(int id, String sort_type) {
        int[] post_id_arr = {};
        List<GetPostResponse> results = new ArrayList<GetPostResponse>();
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
        System.out.println("COMMUNITY");
        for(int i: post_id_arr)
            System.out.println(i);
        for(int i=0; i<post_id_arr.length; i++) {
            int post_id = post_id_arr[i];
            GetPostResponse p = createGetPostResponseByPostId(post_id);
            results.add(p);
        }
        return results;
    }

    public List<GetPostResponse> getALlPostsByCOmmunityIdAndNotAllow(int id) {
        int[] post_id_arr = {};
        List<GetPostResponse> results = new ArrayList<GetPostResponse>();
        post_id_arr = postRepo.getAllPostIdByCommunityIdNotAllow(id);
        for(int i: post_id_arr) {
            GetPostResponse p = createGetPostResponseByPostId(i);
            results.add(p);
        }
        return results;
    }

    public List<GetPostResponse> getAllPostsBySearch(String text, String sort_type) {
        int[] post_id_arr = {};
        List<GetPostResponse> results = new ArrayList<GetPostResponse>();
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
        System.out.println("Search post");
        for(int i: post_id_arr)
            System.out.println(i);
        for(int i=0; i<post_id_arr.length; i++) {
            int post_id = post_id_arr[i];
            GetPostResponse p = createGetPostResponseByPostId(post_id);
            results.add(p);
        }
        //if no post found, then find include
//        if(results.size() == 0) {
//            System.out.println("INCLUDE");
//            if(sort_type.equals("new"))
//                post_id_arr = postRepo.getAllPostsBySearchIncludeSortNew(text);
//            if(sort_type.equals("top_day"))
//                post_id_arr = postRepo.getAllPostsBySearchIncludeSortTop(text, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(1, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
//            if(sort_type.equals("top_week"))
//                post_id_arr = postRepo.getAllPostsBySearchIncludeSortTop(text, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(7, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
//            if(sort_type.equals("top_month"))
//                post_id_arr = postRepo.getAllPostsBySearchIncludeSortTop(text, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(31, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
//            if(sort_type.equals("top_year"))
//                post_id_arr = postRepo.getAllPostsBySearchIncludeSortTop(text, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(365, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
//            if(sort_type.equals("top_all_time"))
//                post_id_arr = postRepo.getAllPostsBySearchIncludeSortTopAllTime(text);
//        }
        return results;
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
