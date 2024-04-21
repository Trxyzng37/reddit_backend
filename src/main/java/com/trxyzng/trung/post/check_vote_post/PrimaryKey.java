package com.trxyzng.trung.post.check_vote_post;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class PrimaryKey implements Serializable {
    public int uid;
    public int post_id;
}
