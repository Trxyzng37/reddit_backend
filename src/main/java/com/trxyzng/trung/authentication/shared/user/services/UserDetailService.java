package com.trxyzng.trung.authentication.shared.user.services;

import com.trxyzng.trung.authentication.shared.user.UserEntityRepo;
import com.trxyzng.trung.authentication.shared.user.UserDetail;
import com.trxyzng.trung.authentication.shared.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    UserEntityRepo userEntityRepo;
    public UserDetails loadUserByUsername(String username) {
        System.out.println("Find user using username: " + username);
        UserEntity userEntity = userEntityRepo.findByUsername(username).orElse(new UserEntity());
        return new UserDetail(userEntity);
    }

    public UserDetails loadUserByEmail(String email) {
        System.out.println("Find user using email: " + email);
        UserEntity userEntity = userEntityRepo.findByEmail(email).orElse(new UserEntity());
        return new UserDetail(userEntity);
    }
}
