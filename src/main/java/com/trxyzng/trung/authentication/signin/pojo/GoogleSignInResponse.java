package com.trxyzng.trung.authentication.signin.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class GoogleSignInResponse {
    @JsonProperty("isGoogleSignIn")
    private boolean isGoogleSignIn;
}
