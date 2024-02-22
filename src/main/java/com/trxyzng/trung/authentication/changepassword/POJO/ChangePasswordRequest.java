package com.trxyzng.trung.authentication.changepassword.POJO;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    private String email;
    private String newPassword;
}
