package com.trxyzng.trung.authentication.accesstoken;

import com.trxyzng.trung.user.shared.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "access_token", schema = "SECURITY")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccessTokenEntity {
    @Column(name = "uid", nullable = false, unique = true)
    private int uid;

    @Id
    @Column(name = "access_token", nullable = false, updatable = false)
    private String access_token;

    @ManyToOne
    @JoinColumn(name = "uid",  nullable = false, insertable = false, updatable = false)
    private UserEntity userEntity;

    public AccessTokenEntity(int uid, String token) {
        this.uid = uid;
        this.access_token = token;
    }
}
