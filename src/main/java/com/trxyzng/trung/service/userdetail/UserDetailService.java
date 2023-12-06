package com.trxyzng.trung.service.userdetail;

import com.trxyzng.trung.entity.User;
import com.trxyzng.trung.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    UserRepo userrepo;
    public UserDetails loadUserByUsername(String username) {
        User user = userrepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new UserDetail(user);
    }
}
