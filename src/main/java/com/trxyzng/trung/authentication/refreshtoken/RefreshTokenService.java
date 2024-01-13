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
     * This function assume that the user is exist in the database
     * save refresh token to database
     * Find user with uid in database
     * put the UserEntity to RefreshTokenEntity
     * save the RefreshTokenEntity to database
     *
     * @param uid
     * @param token
     * @throws NullPointerException
     */
    public void SAVE_TOKEN(int uid, String token) throws DataIntegrityViolationException {
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(100003, token);
        refreshTokenRepo.save(refreshTokenEntity);
    }
}

