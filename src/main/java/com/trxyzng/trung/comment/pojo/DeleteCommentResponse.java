package com.trxyzng.trung.comment.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteCommentResponse {
    @JsonProperty("deleted")
    public boolean deleted;
}
