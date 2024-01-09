package com.trxyzng.trung.authentication.refreshtoken;

import com.trxyzng.trung.user.shared.UserEntity;
import com.trxyzng.trung.user.UserEntityRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    @Autowired
    private UserEntityRepo userEntityRepository;

    @Transactional
    public void saveTokenForUser(int userId, String token) throws NullPointerException {
        UserEntity userEntity = userEntityRepository.findById(userId).orElse(new UserEntity());
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(token);
        refreshTokenEntity.setUserEntity(userEntity);
        userEntity.getRefreshTokenEntities().add(refreshTokenEntity);
        userEntityRepository.save(userEntity);
    }
}

