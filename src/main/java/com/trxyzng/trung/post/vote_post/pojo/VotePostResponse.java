package com.trxyzng.trung.post.vote_post.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class VotePostResponse {
    @JsonProperty("post_id")
    public int post_id;
    @JsonProperty("voteUpdated")
    public boolean voteUpdated;
}
