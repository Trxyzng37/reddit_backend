package com.trxyzng.trung.authentication.changepassword.checkpasscode;

import com.trxyzng.trung.authentication.shared.constraints.EmailConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "change_password_passcode", schema = "SECURITY")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangePasswordPasscodeEntity {
    @Id
    @Column(name = "email", nullable = false, updatable = false)
    @EmailConstraint
    private String email;

    @Column(name = "passcode", nullable = false)
    private int passcode;

    @Column(name = "created_at", nullable = false)
    private Instant created_at;

    @Column(name = "expiration_at", nullable = false)
    private Instant expiration_at;

    public ChangePasswordPasscodeEntity(String email, int passcode, Instant created_at) {
        this.email = email;
        this.passcode = passcode;
        this.created_at = created_at;
        this.expiration_at = created_at.plus(3, ChronoUnit.MINUTES);
    }
}
