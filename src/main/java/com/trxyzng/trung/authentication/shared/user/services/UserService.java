package com.trxyzng.trung.authentication.shared.user.services;

import com.trxyzng.trung.authentication.shared.user.UserEntityRepo;
import com.trxyzng.trung.authentication.shared.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserEntityRepo userEntityRepo;
    public UserEntity loadUserByName(String name) {
        return userEntityRepo.findByUsername(name).orElse(new UserEntity());
    }
    public UserEntity SaveUser(UserEntity userEntity) {
        return userEntityRepo.save(userEntity);
    }
}
