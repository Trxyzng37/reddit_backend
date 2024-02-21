package com.trxyzng.trung.authentication.signin.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UsernamePasswordSignInRequest {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
}
