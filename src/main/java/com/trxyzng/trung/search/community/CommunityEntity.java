package com.trxyzng.trung.search.community;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "community", schema = "INFO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommunityEntity {
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "community_id_generator")
    @SequenceGenerator(name="community_id_generator", sequenceName = "seq_community_id", allocationSize = 1)
    @Column(name = "community_id", nullable = false)
    private int community_id;

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
    @Column(name = "icon_base64", nullable = false)
    private String icon_base64;

    public CommunityEntity(String name, String description, Instant created_at, String icon_base64 ) {
        this.name = name;
        this.description = description;
        this.created_at = created_at;
        this.icon_base64 = icon_base64;
    }
}
