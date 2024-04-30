package com.trxyzng.trung.comment.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EditCommentResponse {
    @JsonProperty("edit_comment")
    public boolean edit_comment;
    @JsonProperty("error")
    public String error;
}
