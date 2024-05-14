package com.trxyzng.trung.post.show_post.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class ShowPostResponse {
    @JsonProperty("error_code")
    public int error_code;
}
