package com.trxyzng.trung.post.save_post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

import com.trxyzng.trung.utility.PrimaryKey;

@Entity
@Table(name = "saved_post", schema = "UTILITIES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@IdClass(PrimaryKey.class)
public class SavedPostEntity {
    @Id
    @NotNull
    @Column(name = "uid", nullable = false)
    private int uid;

    @Id
    @NotNull
    @Column(name = "post_id", nullable = false)
    private int post_id;

    @NotNull
    @Column(name = "saved", nullable = false)
    private int saved;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant created_at;
}
