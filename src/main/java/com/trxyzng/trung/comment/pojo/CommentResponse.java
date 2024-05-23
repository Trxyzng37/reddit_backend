package com.trxyzng.trung.comment.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@Setter
public class CommentResponse {
    @JsonProperty("_id")
    public int _id;
    @JsonProperty("uid")
    public int uid;
    @JsonProperty("username")
    public String username;
    @JsonProperty("user_icon")
    public String user_icon;
    @JsonProperty("parent_id")
    public int parent_id;
    @JsonProperty("content")
    public String content;
    @JsonProperty("level")
    public int level;
    @JsonProperty("created_at")
    public Instant created_at;
    @JsonProperty("vote")
    public int vote;
    @JsonProperty("deleted")
    public boolean deleted;
}
