package com.trxyzng.trung.post.save_post.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class SavedPostResponse {
    @JsonProperty("saved")
    public int saved;
}
