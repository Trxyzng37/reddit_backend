package com.trxyzng.trung.comment;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class Comment {
    @Id
    private int _id;

    @NotNull
    private int uid;

    @NotNull
    private int parent_id;

    @NotNull
    private String content;

    @NotNull
    private Instant created_at;

    @NotNull
    private int vote;

    @NotNull
    private int level;

    @NotNull
    private boolean deleted;

    public Comment() {
        this._id = 0;
        this.uid = 0;
        this.content = "";
        this.created_at = null;
        this.vote = 0;
        this.deleted = false;
    }
}
