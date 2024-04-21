package com.trxyzng.trung.post.check_vote_post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.trxyzng.trung.authentication.shared.user.UserEntity;
import com.trxyzng.trung.post.PostEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vote_info", schema = "INFO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@IdClass(PrimaryKey.class)
public class CheckVotePostEntity {
    @Id
    @NotNull
    @Column(name = "uid", nullable = false)
    private int uid;

    @Id
    @NotNull
    @Column(name = "post_id", nullable = false)
    private int post_id;

    @NotNull
    @Column(name = "vote_type", nullable = false)
    private String vote_type;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name="uid", nullable = false, insertable=false, updatable=false)
    private UserEntity userEntity;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name="post_id", nullable = false, insertable=false, updatable=false)
    private PostEntity postEntity;

    public CheckVotePostEntity(int uid, int postId, String voteType) {
        this.uid = uid;
        this.post_id = postId;
        this.vote_type = voteType;
    }
}
