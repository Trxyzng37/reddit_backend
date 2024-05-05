package com.trxyzng.trung.post.get_post.pojo;

import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class GetPostResponse {
    public int post_id;
    public String type;
    public int uid;
    public String username;
    public String username_icon;
    public int community_id;
    public String community_name;
    public String community_icon;
    public String title;
    public String content;
    public Instant created_at;
    public int vote;
}
