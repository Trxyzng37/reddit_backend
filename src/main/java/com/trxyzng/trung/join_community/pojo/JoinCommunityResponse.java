package com.trxyzng.trung.join_community.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor@Setter
public class JoinCommunityResponse {
    @JsonProperty("join_community")
    public int join_community;
    @JsonProperty("error_code")
    public int error_code;
}
