package com.trxyzng.trung.authentication.signup.choose_username.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserNameExistResponse {
    @JsonProperty("usernameExist")
    private boolean usernameExist;
}
