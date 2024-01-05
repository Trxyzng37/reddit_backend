package com.trxyzng.trung.entity;

import com.trxyzng.trung.utility.constraint.EmailConstraint;
import com.trxyzng.trung.utility.constraint.PassWordConstraint;
import com.trxyzng.trung.utility.constraint.UserNameConstraint;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id")
//    @SequenceGenerator(name = "user_id", sequenceName="user_id_generator", allocationSize = 1)
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RefreshToken> refreshTokens;

    public User(String name, String password, String email) {
        this.username = name;
        this.password = password;
        this.email = email;
    }

}

