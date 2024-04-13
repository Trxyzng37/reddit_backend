package com.trxyzng.trung.post.getpost.pojo;

import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class GetPostResponse {
    public int post_id;
    public String type;
    public String username;
    public String username_icon;
    public String community_name;
    public String community_icon;
    public String title;
    public String content;
    public Instant created_at;
    public int vote;
}
