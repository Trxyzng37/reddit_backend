package com.trxyzng.trung.authentication.shared.oathuser;

import com.trxyzng.trung.authentication.shared.constraints.EmailConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "oath2_user", schema = "USER_DATA")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OathUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name="user_generator", sequenceName = "seq_uid", allocationSize = 1)
    @NotNull
    @Column(name = "uid", nullable = false)
    private int id;

    @NotNull
    @Column(name = "email", nullable = false)
    @EmailConstraint
    private String email;

    public OathUserEntity(String email) {
        this.email = email;
    }
}
