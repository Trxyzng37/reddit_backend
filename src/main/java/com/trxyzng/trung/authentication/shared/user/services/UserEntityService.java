package com.trxyzng.trung.authentication.shared.user.services;

import com.trxyzng.trung.authentication.shared.user.UserEntityRepo;
import com.trxyzng.trung.authentication.shared.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEntityService {
    @Autowired
    private UserEntityRepo userEntityRepo;
    public UserEntity findUserEntityByUsername(String name) {
        return userEntityRepo.findByUsername(name).orElse(new UserEntity());
    }
    public UserEntity saveUserEntity(UserEntity userEntity) {
        return userEntityRepo.save(userEntity);
    }

    public UserEntity findUserEntityByEmail(String email) {
        return userEntityRepo.findByEmail(email).orElse(new UserEntity());
    }

//    public boolean isEmailExistInDB(String email) {
//        return userEntityRepo.isEMailExistInDB(email);
//    }
}

