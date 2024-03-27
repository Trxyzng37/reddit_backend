package com.trxyzng.trung.create_post;

import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;


interface PostResponse {
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
    int getPost_id();
    Instant getCreated_at();
    int getVote();
    @Value("#{target.communityEntity.name}")
    String getCommunityName();

    @Value("#{target.userEntity.username}")
    String getUserName();

    @Value("#{target.communityEntity.icon_base64}")
    String getCommunityIcon();
}
