package com.trxyzng.trung.user.shared.services;

import com.trxyzng.trung.user.shared.UserDetail;
import com.trxyzng.trung.user.shared.UserEntity;
import com.trxyzng.trung.user.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserByEmailService {
    @Autowired
    private UserEntityRepo userrepo;
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException{
        UserEntity userEntity = userrepo.findByEmail(email).orElse(new UserEntity());
        if (userEntity == null) {
            throw new UsernameNotFoundException("UserEntity not found with email: " + email);
        }
        return new UserDetail(userEntity);
    }
}
