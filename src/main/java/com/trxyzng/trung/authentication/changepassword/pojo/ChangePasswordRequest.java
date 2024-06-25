package com.trxyzng.trung.authentication.changepassword.pojo;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    private String email;
    private String newPassword;
}
