package com.trxyzng.trung.post.get_post.pojo;

import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class GetDetailPostResponse {
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
    public int allow;
    public int deleted;
    public Integer join;
    public String voteType;
    public Integer save;
    public int communityOwner;
    public int deleted_by;
    public Instant deleted_at;
    public Instant allowed_at;
    public int editted;
    public Instant editted_at;

//    public GetPostResponse(int post_id, String type, int uid, String username, String username_icon, int community_id, String community_name, String community_icon, String title, String content, Instant created_at, int vote, int allow, int deleted) {
//        this.post_id = post_id;
//        this.type = type;
//        this.uid = uid;
//        this.username = username;
//        this.username_icon = username_icon;
//        this.community_id = community_id;
//        this.community_name = community_name;
//        this.title = title;
//        this.content = content;
//        this.created_at = created_at;
//        this. vote = vote;
//        this.allow = allow;
//        this.deleted = deleted;
//    }
}
