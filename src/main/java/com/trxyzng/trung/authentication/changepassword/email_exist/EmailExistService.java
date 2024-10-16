package com.trxyzng.trung.authentication.changepassword.email_exist;

import com.trxyzng.trung.authentication.shared.user.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailExistService {
    @Autowired
    UserEntityRepo userEntityRepo;
    public boolean isUserEntityByEmailExist(String email) {
        int isEmail = userEntityRepo.existByEmail(email);
        return isEmail == 0 ? false : true;
    }
}
