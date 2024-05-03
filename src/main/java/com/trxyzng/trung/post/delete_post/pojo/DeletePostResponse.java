package com.trxyzng.trung.post.delete_post.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeletePostResponse {
    @JsonProperty("deleted")
    public boolean deleted;
    @JsonProperty("error")
    public String error;
}
