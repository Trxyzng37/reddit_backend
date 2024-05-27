package com.trxyzng.trung.authentication.signup.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResendPasscodeRequest {
    private String email;
}
