package com.trxyzng.trung.authentication.signup.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class IsSignUp {
    @JsonProperty("isSignUp")
    private boolean isSignUp;
    @JsonProperty("usernameError")
    private boolean usernameError;
    @JsonProperty("emailError")
    private boolean emailError;
}
