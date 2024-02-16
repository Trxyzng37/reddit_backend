package com.trxyzng.trung.authentication.shared.user.services;

import com.trxyzng.trung.authentication.shared.user.UserEntityRepo;
import com.trxyzng.trung.authentication.shared.user.UserDetail;
import com.trxyzng.trung.authentication.shared.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserByEmailService {
    @Autowired
    private UserEntityRepo userrepo;

    /**
     * Find UserEntity in the database that has the email address.
     * @param email the email address that user want to log-in.
     * @return an UserDetails object if successful, else an empty UserDetail object.
     * @throws UsernameNotFoundException
     */
    public UserDetail loadUserByEmail(String email) {
        UserEntity userEntity = userrepo.findByEmail(email).orElse(new UserEntity());
        return new UserDetail(userEntity);
    }
}
