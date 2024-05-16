package com.trxyzng.trung.recent_visit.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "recent_visitted_post", schema = "UTILITIES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@IdClass(PrimaryKey.class)
public class RecentVisitPostEntity {

    @Id
    @NotNull
    @Column(name = "uid", nullable = false)
    private int uid;

    @Id
    @NotNull
    @Column(name = "post_id", nullable = false)
    private int post_id;

    @NotNull
    @Column(name = "visitted_time", nullable = false)
    private Instant visitted_time;
}
