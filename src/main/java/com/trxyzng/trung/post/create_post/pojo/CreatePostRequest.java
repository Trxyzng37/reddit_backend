package com.trxyzng.trung.post.create_post.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CreatePostRequest {
    public String type;
    public int uid;
    public int community_id;
    public String title;
    public String content;
    public String created_at;
}
