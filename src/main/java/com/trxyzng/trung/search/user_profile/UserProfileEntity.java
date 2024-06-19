package com.trxyzng.trung.search.user_profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trxyzng.trung.authentication.shared.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public UserProfileEntity(int uid, String username, String description, Instant created_at) {
        this.uid = uid;
        this.username = username;
        this.description = description;
        this.created_at = created_at;
        this.comment_karma = 0;
        this.post_karma = 0;
        this.avatar = readAvatar();
    }

    private String readAvatar() {
        try {
            return new String(Files.readAllBytes(Paths.get("src/main/java/com/trxyzng/trung/authentication/shared/avatar.txt")));
        }
        catch (Exception e) {
            return "error";
        }
    }
}
