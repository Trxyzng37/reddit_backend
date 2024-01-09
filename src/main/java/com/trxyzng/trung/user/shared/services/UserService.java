package com.trxyzng.trung.user.shared.services;

import com.trxyzng.trung.user.shared.UserEntity;
import com.trxyzng.trung.user.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserEntityRepo userEntityRepo;
    /**
     * This method performs a specific function.
//     * @param name too
     */
    public UserEntity loadUserByName(String name) {
        return userEntityRepo.findByUsername(name).orElse(new UserEntity());
    }
    public UserEntity SaveUser(UserEntity userEntity) {
        return userEntityRepo.save(userEntity);
    }
}
