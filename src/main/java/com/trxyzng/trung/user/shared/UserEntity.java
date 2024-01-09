package com.trxyzng.trung.user.shared;

import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenEntity;
import com.trxyzng.trung.user.constraints.EmailConstraint;
import com.trxyzng.trung.user.constraints.PassWordConstraint;
import com.trxyzng.trung.user.constraints.UserNameConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "user_data")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name="user_generator", sequenceName = "user_id_generator", allocationSize = 1)
    @NotNull
    @Column(name = "id", nullable = false)
    private int id;

    @NotNull
    @Column(name = "username", nullable = false)
    @UserNameConstraint
    private String username;
    @NotNull
    @Column(name = "password", nullable = false)
    @PassWordConstraint
    private String password;
    @NotNull
    @Column(name = "email", nullable = false)
    @EmailConstraint
    private String email;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<RefreshTokenEntity> refreshTokenEntities;

    public UserEntity(String name, String password, String email) {
        this.username = name;
        this.password = password;
        this.email = email;
    }

}

