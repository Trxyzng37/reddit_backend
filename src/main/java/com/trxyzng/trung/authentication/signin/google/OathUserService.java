package com.trxyzng.trung.authentication.signin.google;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OathUserService {
    @Autowired
    OathUserRepo oathUserRepo;

    public OathUserEntity findOathUserEntityByEmail(String email) {
        return oathUserRepo.findByEmail(email).orElse(new OathUserEntity());
    }
}
