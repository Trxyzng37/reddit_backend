package com.trxyzng.trung.authentication.shared.oathuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OathUserEntityService {
    @Autowired
    OathUserRepo oathUserRepo;

    public OathUserEntity findOathUserEntityByEmail(String email) {
        return oathUserRepo.findByEmail(email).orElse(new OathUserEntity());
    }

    public OathUserEntity saveOathUserEntity(OathUserEntity oathUserEntity) {
        return oathUserRepo.save(oathUserEntity);
    }

    public boolean isEmailExistInDB(String email) {
        return oathUserRepo.isEMailExistInDB(email);
    }
}
