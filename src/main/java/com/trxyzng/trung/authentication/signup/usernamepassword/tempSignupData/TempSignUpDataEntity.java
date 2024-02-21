package com.trxyzng.trung.authentication.signup.usernamepassword.tempSignupData;

import com.trxyzng.trung.authentication.shared.constraints.EmailConstraint;
import com.trxyzng.trung.authentication.shared.constraints.PassWordConstraint;
import com.trxyzng.trung.authentication.shared.constraints.RoleConstraint;
import com.trxyzng.trung.authentication.shared.constraints.UserNameConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sign_up_data", schema = "TEMPORARY")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TempSignUpDataEntity {
    @NotNull
    @Column(name = "username", nullable = false)
    @UserNameConstraint
    private String username;

    @NotNull
    @Column(name = "password", nullable = false)
    @PassWordConstraint
    private String password;

    @Id
    @NotNull
    @Column(name = "email", nullable = false)
    @EmailConstraint
    private String email;
}
