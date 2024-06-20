package com.trxyzng.trung.authentication.refreshtoken;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "refresh_token", schema = "SECURITY")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshTokenEntity {
    @Column(name = "uid", nullable = false, unique = true)
    private int uid;

    @Id
    @Column(name = "refresh_token", nullable = false, updatable = false)
    private String refresh_token;

//    @ManyToOne
//    @JoinColumn(name = "uid",  nullable = false, insertable = false, updatable = false)
//    private UserEntity userEntity;
}
