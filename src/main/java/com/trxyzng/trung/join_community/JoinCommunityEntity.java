package com.trxyzng.trung.join_community;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "join_community", schema = "UTILITIES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@IdClass(PrimaryKey.class)
public class JoinCommunityEntity {
    @Id
    @NotNull
    @Column(name = "uid", nullable = false)
    private int uid;

    @Id
    @NotNull
    @Column(name = "community_id", nullable = false)
    private int community_id;

    @NotNull
    @Column(name = "subscribed", nullable = false)
    private int subscribed;
}
