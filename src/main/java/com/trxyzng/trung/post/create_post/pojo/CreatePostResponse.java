package com.trxyzng.trung.post.create_post.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreatePostResponse {
    @JsonProperty("CREATED")
    public boolean CREATED;
    @JsonProperty("POST_ID")
    public int POST_ID;
}

//public interface CreatePostResponse {
//    CommunityInfo getCommunityEntity();
//    UserInfo getUserEntity();
//    interface CommunityInfo {
//        String getName();
//        String getIcon_base64();
//    }
//    interface UserInfo {
////        @Transient
//        String getUsername();
//    }
//    int getPost_id();
//    Instant getCreated_at();
//    int getVote();
//    @Value("#{target.communityEntity.name}")
//    String getCommunityName();
//
//    @Value("#{target.userEntity.username}")
//    String getUserName();
//
//    @Value("#{target.communityEntity.icon_base64}")
//    String getCommunityIcon();
//}