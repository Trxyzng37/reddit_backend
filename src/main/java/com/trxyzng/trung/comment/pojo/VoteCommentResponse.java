package com.trxyzng.trung.comment.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class VoteCommentResponse {
    @JsonProperty("vote_updated")
    boolean vote_updated;
    @JsonProperty("error")
    String error;
}
