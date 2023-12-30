package com.trxyzng.trung.service.userdetail;

import com.trxyzng.trung.entity.RefreshToken;
import com.trxyzng.trung.entity.User;
import com.trxyzng.trung.repository.UserRepo;
import com.trxyzng.trung.utility.EmptyObjectCheckUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    @Autowired
    private UserRepo userRepository;

    @Transactional
    public void saveTokenForUser(int userId, String token) {
        User user = userRepository.findById(userId).orElse(new User());
        if (EmptyObjectCheckUtils.is_empty(user)) {
            System.out.println("Null user");
        }
        else {
            RefreshToken refreshToken = new RefreshToken(token);
            refreshToken.setUser(user);
            user.getRefreshTokens().add(refreshToken);
            userRepository.save(user);
        }
    }
}

