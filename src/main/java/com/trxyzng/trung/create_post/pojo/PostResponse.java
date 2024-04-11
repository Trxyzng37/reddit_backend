package com.trxyzng.trung.create_post.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;

@AllArgsConstructor
public class PostResponse {
    @JsonProperty("CREATED")
    public boolean CREATED;
    @JsonProperty("POST_ID")
    public int POST_ID;
}

//public interface PostResponse {
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