package com.trxyzng.trung.post.get_post.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LinkPostData {
    @JsonProperty("link")
    public String link;
    @JsonProperty("title")
    public String title;
    @JsonProperty("image")
    public String image;
    @JsonProperty("url")
    public String url;

}
