package com.trxyzng.trung.post.save_post.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SavedPostRequest {
    private int uid;
    private int post_id;
    private int saved;
}
