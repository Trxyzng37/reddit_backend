package com.trxyzng.trung.service.userdetail;

import com.trxyzng.trung.entity.RefreshToken;
import com.trxyzng.trung.entity.User;
import com.trxyzng.trung.repository.RefreshTokenRepo;
import com.trxyzng.trung.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveTokenService {

    @Autowired
    private UserRepo userRepository;

    @Transactional
    public void saveTokenForUser(int userId, String token) {
        User user = userRepository.findById(userId);
        if (user != null) {
            RefreshToken refreshToken = new RefreshToken(token);
            refreshToken.setUser(user);
            user.getRefreshTokens().add(refreshToken);
            userRepository.save(user);
        }
        else {
            System.out.println("Null user");
        }
    }
}

