package com.trxyzng.trung.authentication.signup.choose_username.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SelectUsernameRequest {
    private String email;
    private String username;
}
