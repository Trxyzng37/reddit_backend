package com.trxyzng.trung.search.user_profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trxyzng.trung.authentication.shared.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Entity
@Table(name = "user_profile", schema = "INFO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProfileEntity {
    @Id
    @NotNull
    @Column(name = "uid", nullable = false)
    private int uid;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant created_at;

    @NotNull
    @Column(name = "post_karma", nullable = false)
    private int post_karma;

    @NotNull
    @Column(name = "comment_karma", nullable = false)
    private int comment_karma;

    @NotNull
    @Column(name = "avatar", nullable = false)
    private String avatar;

//    @JsonIgnore
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false)
//    private UserEntity userEntity;

//    public UserProfileEntity(int uid, String name, String description, Instant created_at, int karma, String icon_base64 ) {
//        this.uid = uid;
//        this.name = name;
//        this.description = description;
//        this.created_at = created_at;
//        this.karma = karma;
//        this.icon_base64 = icon_base64;
//    }
}
