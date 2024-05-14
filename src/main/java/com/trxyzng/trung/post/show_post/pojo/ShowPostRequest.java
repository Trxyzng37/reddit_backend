package com.trxyzng.trung.post.show_post.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShowPostRequest {
    private int uid;
    private int post_id;
    private int show;
}
