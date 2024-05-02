package com.trxyzng.trung.post.edit_post.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class EditPostResponse {
    @JsonProperty("edit_post")
    public boolean edit_post;
    @JsonProperty("error")
    public String error;
}
