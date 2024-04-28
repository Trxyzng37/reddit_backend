package com.trxyzng.trung.comment.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentStatus {
    int _id;
    int uid;
    String vote_type;
}
