package com.trxyzng.trung.search.community;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "community", schema = "INFO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommunityEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "community_id_generator")
    @SequenceGenerator(name="community_id_generator", sequenceName = "seq_community_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private int id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "uid", nullable = false)
    private int uid;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant created_at;

    @NotNull
    @Column(name = "subscriber_count", nullable = false)
    private int subscriber_count;

    @NotNull
    @Column(name = "avatar", nullable = false)
    private String avatar;

    public CommunityEntity(int uid, String name, String description, String avatar) {
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.avatar = avatar;
        this.created_at = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        this.subscriber_count = 1;
    }
}
