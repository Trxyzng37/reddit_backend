package com.trxyzng.trung.post.check_vote_post.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class CheckVotePostResponse {
    @JsonProperty("vote_type")
    public String vote_type;
}
