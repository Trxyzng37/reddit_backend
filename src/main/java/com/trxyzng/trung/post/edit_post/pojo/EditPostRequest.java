package com.trxyzng.trung.post.edit_post.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EditPostRequest {
    private int post_id;
    private int uid;
    private String type;
    private String title;
    private String content;
}
