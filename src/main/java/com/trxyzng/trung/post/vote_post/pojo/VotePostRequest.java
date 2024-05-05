package com.trxyzng.trung.post.vote_post.pojo;

import lombok.Getter;

@Getter
public class VotePostRequest {
    public int post_id;
    public int vote;
    public int uid;
    public String type;
}
