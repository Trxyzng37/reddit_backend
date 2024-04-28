package com.trxyzng.trung.comment.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CreateCommentRequest {
    @JsonProperty("post_id")
    private int post_id;
    @JsonProperty("uid")
    private int uid;
    @JsonProperty("parent_id")
    private int parent_id;
    @JsonProperty("content")
    private  String content;
    @JsonProperty("level")
    private int level;
}
