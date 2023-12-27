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

    @Autowired
    private RefreshTokenRepo refreshTokenRepository;

    @Transactional
    public void saveTokenForUser(int userId, String token) {
        // Find the user from the database
        User user = userRepository.findById(userId);

        if (user != null) {
            // Create a new RefreshToken
            RefreshToken refreshToken = new RefreshToken(token);

            // Set the user for the RefreshToken
            refreshToken.setUser(user);

            // Add the RefreshToken to the user's list
            user.getRefreshTokens().add(refreshToken);

            // Save the User, and the CascadeType.ALL will cascade to save the RefreshToken
            userRepository.save(user);
        }
    }
}

