package com.trxyzng.trung.authentication.shared.user.services;

import com.trxyzng.trung.authentication.shared.user.UserEntityRepo;
import com.trxyzng.trung.authentication.shared.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEntityService {
    @Autowired
    private UserEntityRepo userEntityRepo;

    public boolean isUserEntityByUsernameOrEmailExist(String username, String email) {
        int usernameExist = userEntityRepo.existByUsername(username);
        int emailExist = userEntityRepo.existByEmail(email);
        return usernameExist == 1 || emailExist == 1;
    }

    public boolean isUserEntityByUidExist(int uid) {
        return userEntityRepo.isUserEntityByUidExist(uid) == 1;
    }

    public UserEntity saveUserEntity(UserEntity userEntity) {
        return userEntityRepo.save(userEntity);
    }

    public boolean existByEmail(String email) {
        return userEntityRepo.existByEmail(email) == 1 ? true : false;
    }

    public UserEntity findUserEntityByUsername(String username) {
        return userEntityRepo.findByUsername(username).orElse(new UserEntity());
    }

    public int isUsernameExist(String username) {
        return userEntityRepo.existByUsername(username);
    }

//    public Integer findUidByUsername(String username) {
//        return userEntityRepo.findUidByUsername(username).orElse(0);
//    }

//    public boolean isEmailExistInDB(String email) {
//        return userEntityRepo.isEMailExistInDB(email);
//    }
}

