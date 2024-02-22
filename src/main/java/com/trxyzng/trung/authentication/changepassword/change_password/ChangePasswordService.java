package com.trxyzng.trung.authentication.changepassword.change_password;

import com.trxyzng.trung.authentication.shared.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordService {
    @Autowired
    ChangePasswordRepo changePasswordRepo;

    public void updatePasswordForEmail(String email, String password) {
        changePasswordRepo.updatePasswordForEmail(email, password);
    }

    public String findOldPasswordByEmail(String email) {
        return changePasswordRepo.findPasswordByEmail(email).orElse("");
    }
}
