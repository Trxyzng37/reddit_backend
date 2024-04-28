package com.trxyzng.trung.comment.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CreateCommentResponse {
    @JsonProperty("comment_created")
    private boolean comment_created;
}
