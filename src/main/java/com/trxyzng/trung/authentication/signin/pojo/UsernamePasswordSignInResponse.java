package com.trxyzng.trung.authentication.signin.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UsernamePasswordSignInResponse {
    @JsonProperty("isSignIn")
    private boolean isSignIn;
    @JsonProperty("passwordError")
    private boolean passwordError;
    @JsonProperty("uid")
    private int uid;
}
