package com.trxyzng.trung.join_community.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JoinCommunityRequest {
    private int uid;
    private  int community_id;
    private int subscribed;
}
