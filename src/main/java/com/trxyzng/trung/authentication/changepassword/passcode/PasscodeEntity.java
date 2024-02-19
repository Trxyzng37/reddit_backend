package com.trxyzng.trung.authentication.changepassword.passcode;

import com.trxyzng.trung.authentication.shared.constraints.EmailConstraint;
import com.trxyzng.trung.authentication.shared.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Entity
@Table(name = "passcode", schema = "SECURITY")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PasscodeEntity {
    @Id
    @Column(name = "email", nullable = false, updatable = false)
    @EmailConstraint
    private String email;

    @Column(name = "passcode", nullable = false)
    private int passcode;

    @Column(name = "created_at", nullable = false)
    private Instant created_at;

    @Column(name = "expiration_at", nullable = false, updatable = false, insertable = false)
    private Instant expiration_at;

    @Column(name = "change_password", nullable = false, columnDefinition="BIT")
    private boolean change_password;

    @Column(name = "confirm_email", nullable = false, columnDefinition="BIT")
    private boolean confirm_password;

    @ManyToOne
    @JoinColumn(name = "email",  nullable = false, insertable = false, updatable = false)
    private UserEntity userEntity;

    public PasscodeEntity(String email, int passcode, Instant created_at) {
        this.email = email;
        this.passcode = passcode;
        this.created_at = created_at;
    }
}
