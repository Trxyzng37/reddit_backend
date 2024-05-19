package com.trxyzng.trung.post.allow_post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AllowPostRequest {
    private int post_id;
    private int allow;
}
