package com.trxyzng.trung.authentication.changepassword.change_password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordService {
    @Autowired
    ChangePasswordRepo changePasswordRepo;

    public void updatePasswordForEmail(String email, String password) {
        changePasswordRepo.updatePasswordForEmail(email, password);
    }

    public String getPasswordByEmail(String email) {
        String currentPassword = changePasswordRepo.SelectPasswordByEmail(email).orElse("");
        return currentPassword;
    }
}
