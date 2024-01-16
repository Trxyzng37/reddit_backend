package com.trxyzng.trung.authentication.accesstoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenService {
    @Autowired
    AccessTokenRepo accessTokenRepo;
    public AccessTokenEntity saveAccessToken(int uid, String token) {
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity(uid, token);
        return accessTokenRepo.save(accessTokenEntity);
    }
}
