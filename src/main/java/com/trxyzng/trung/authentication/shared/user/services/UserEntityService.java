package com.trxyzng.trung.authentication.shared.user.services;

import com.trxyzng.trung.authentication.shared.user.UserEntityRepo;
import com.trxyzng.trung.authentication.shared.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEntityService {
    @Autowired
    private UserEntityRepo userEntityRepo;

    public boolean isUserEntityByUsernameOrEmailExist(String username, String email) {
        int usernameExist = userEntityRepo.isUserEntityByUsernameExist(username);
        int emailExist = userEntityRepo.isUserEntityByEmailExist(email);
        return usernameExist == 1 || emailExist == 1;
    }

    public boolean isUserEntityByUidExist(int uid) {
        return userEntityRepo.isUserEntityByUidExist(uid) == 1;
    }

    public UserEntity saveUserEntity(UserEntity userEntity) {
        return userEntityRepo.save(userEntity);
    }

    public UserEntity findUserEntityByEmail(String email) {
        return userEntityRepo.findByEmail(email).orElse(new UserEntity());
    }

    public UserEntity findUserEntityByUsername(String username) {
        return userEntityRepo.findByUsername(username).orElse(new UserEntity());
    }

    public Integer findUidByUsername(String username) {
        return userEntityRepo.findUidByUsername(username).orElse(0);
    }

//    public boolean isEmailExistInDB(String email) {
//        return userEntityRepo.isEMailExistInDB(email);
//    }
}

