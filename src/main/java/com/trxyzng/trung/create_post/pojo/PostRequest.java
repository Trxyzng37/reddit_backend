package com.trxyzng.trung.create_post.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.Instant;

@Getter
public class PostRequest {
    @JsonProperty("community_name")
    private String community_name;

    @JsonProperty("username")
    private String username;

    @JsonProperty("created_at")
    private Instant created_at;

    @JsonProperty("community_id")
    private String community_id;

}
