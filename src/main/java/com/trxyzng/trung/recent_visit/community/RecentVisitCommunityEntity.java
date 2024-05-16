package com.trxyzng.trung.recent_visit.community;

import com.trxyzng.trung.recent_visit.community.PrimaryKey;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "recent_visitted_community", schema = "UTILITIES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@IdClass(PrimaryKey.class)
public class RecentVisitCommunityEntity {
    @Id
    @NotNull
    @Column(name = "uid", nullable = false)
    private int uid;

    @Id
    @NotNull
    @Column(name = "community_id", nullable = false)
    private int community_id;

    @NotNull
    @Column(name = "visitted_time", nullable = false)
    private Instant visitted_time;
}
