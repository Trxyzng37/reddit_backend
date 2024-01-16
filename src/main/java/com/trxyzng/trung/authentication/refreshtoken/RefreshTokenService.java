package com.trxyzng.trung.authentication.refreshtoken;

import com.trxyzng.trung.user.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
    @Autowired
    private UserEntityRepo userEntityRepo;
    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    /**
     * Save refresh token to the database.
     * This function assume that the user uid is already exist in the database.
     * @param uid the unique indentifier of the user in database.
     * @param token the refresh token value as string.
     * @return void
     * @throws DataIntegrityViolationException
     */
    public void saveRefreshToken(int uid, String token) {
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(uid, token);
        refreshTokenRepo.save(refreshTokenEntity);
    }
}

