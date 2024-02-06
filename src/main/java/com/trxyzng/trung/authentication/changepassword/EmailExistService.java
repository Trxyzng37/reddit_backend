package com.trxyzng.trung.authentication.changepassword;

import com.trxyzng.trung.user.UserEntityRepo;
import com.trxyzng.trung.user.shared.UserEntity;
import com.trxyzng.trung.utility.EmptyEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailExistService {
    @Autowired
    UserEntityRepo userEntityRepo;
    public boolean isUserByEmailExist(String email) {
        UserEntity user = userEntityRepo.findByEmail(email).orElse(new UserEntity());
        return !EmptyEntityUtils.isEmptyEntity(user);
    }
}
