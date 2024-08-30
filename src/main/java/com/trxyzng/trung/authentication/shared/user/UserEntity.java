package com.trxyzng.trung.authentication.shared.user;

import com.trxyzng.trung.authentication.shared.constraints.EmailConstraint;
import com.trxyzng.trung.authentication.shared.constraints.PassWordConstraint;
import com.trxyzng.trung.authentication.shared.constraints.UserNameConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", schema = "USER_DATA")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name="user_generator", sequenceName = "seq_uid", allocationSize = 1)
    @NotNull
    @Column(name = "uid", nullable = false)
    private int uid;

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

    public UserEntity(String name, String password, String email) {
        this.username = name;
        this.password = password;
        this.email = email;
    }
}

