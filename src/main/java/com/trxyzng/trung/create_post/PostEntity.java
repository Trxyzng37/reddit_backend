package com.trxyzng.trung.create_post;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.trxyzng.trung.authentication.shared.user.UserEntity;
import com.trxyzng.trung.search.community.CommunityEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "post", schema = "INFO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_generator")
    @SequenceGenerator(name="post_id_generator", sequenceName = "seq_post_id", allocationSize = 1)
    @Column(name = "post_id", nullable = false)
    private int post_id;

    @NotNull
    @Column(name = "community_id", nullable = false)
    private int community_id;

    @NotNull
    @Column(name = "uid", nullable = false)
    private int uid;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant created_at;

    @NotNull
    @Column(name = "vote", nullable = false)
    private int vote;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="community_id", nullable = false, insertable=false, updatable=false)
    private CommunityEntity communityEntity;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="uid", nullable = false, insertable=false, updatable=false)
    private UserEntity userEntity;
}
