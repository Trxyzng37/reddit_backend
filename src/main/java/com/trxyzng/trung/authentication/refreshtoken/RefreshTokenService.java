package com.trxyzng.trung.authentication.refreshtoken;

import com.trxyzng.trung.user.shared.UserEntity;
import com.trxyzng.trung.user.UserEntityRepo;
import com.trxyzng.trung.utility.EmptyEntityUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
//@Transactional
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
    public void saveRefreshToken(int uid, String token) throws DataIntegrityViolationException {
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(uid, token);
        refreshTokenRepo.save(refreshTokenEntity);
    }
}

