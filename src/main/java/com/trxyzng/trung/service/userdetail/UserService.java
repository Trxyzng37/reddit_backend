package com.trxyzng.trung.service.userdetail;

import com.trxyzng.trung.entity.User;
import com.trxyzng.trung.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    /**
     * This method performs a specific function.
//     * @param name too
     */
    public User loadUserByName(String name) {
        return userRepo.findByUsername(name).orElse(new User());
    }
    public User SaveUser(User user) {
        return userRepo.save(user);
    }
}
