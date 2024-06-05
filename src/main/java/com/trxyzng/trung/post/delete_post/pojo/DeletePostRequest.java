package com.trxyzng.trung.post.delete_post.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeletePostRequest {
    private int post_id;
    private int uid;
    private String deleted_by;
}
