package com.trxyzng.trung.user.shared.services;

import com.trxyzng.trung.user.shared.UserDetail;
import com.trxyzng.trung.user.shared.UserEntity;
import com.trxyzng.trung.user.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    UserEntityRepo userrepo;
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = userrepo.findByUsername(username).orElse(new UserEntity());
        return new UserDetail(userEntity);
    }
}
