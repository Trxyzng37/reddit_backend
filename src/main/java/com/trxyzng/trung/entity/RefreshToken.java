package com.trxyzng.trung.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "refresh_token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshToken {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, insertable=false)
    private int id;

    @Id
    @Column(name = "refresh_token")
    private String refresh_token;

    @ManyToOne
    @JoinColumn(name = "id",  nullable = false)
    private User user;

    public RefreshToken(String token) {
        this.refresh_token = token;
    }
}
