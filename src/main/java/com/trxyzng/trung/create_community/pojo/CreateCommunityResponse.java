package com.trxyzng.trung.create_community.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class CreateCommunityResponse {
    @JsonProperty("community_id")
    public int community_id;
    @JsonProperty("error_code")
    public int error_code;
}
