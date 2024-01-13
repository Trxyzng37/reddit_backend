package com.trxyzng.trung.user.shared.services;

import com.trxyzng.trung.user.shared.UserDetail;
import com.trxyzng.trung.user.shared.UserEntity;
import com.trxyzng.trung.user.UserEntityRepo;
import com.trxyzng.trung.utility.EmptyEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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
