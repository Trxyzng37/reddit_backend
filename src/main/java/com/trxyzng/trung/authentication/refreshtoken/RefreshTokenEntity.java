package com.trxyzng.trung.authentication.refreshtoken;

import com.trxyzng.trung.user.shared.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "refresh_token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshTokenEntity {
    @Column(name = "id", updatable = false, insertable=false, nullable = false)
    private int id;

    @Id
    @Column(name = "refresh_token", nullable = false)
    private String refresh_token;

    @ManyToOne
    @JoinColumn(name = "id",  nullable = false)
    private UserEntity userEntity;

    public RefreshTokenEntity(String token) {
        this.refresh_token = token;
    }
}
