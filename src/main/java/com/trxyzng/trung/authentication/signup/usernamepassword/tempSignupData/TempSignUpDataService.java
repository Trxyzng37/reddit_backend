package com.trxyzng.trung.authentication.signup.usernamepassword.tempSignupData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TempSignUpDataService {
    @Autowired
    TempSignUpDataRepo tempSignUpDataRepo;

    public TempSignUpDataEntity findTempSignUpDataEntityByEmail(String email) {
        return tempSignUpDataRepo.findByEmail(email).orElse(new TempSignUpDataEntity());
    }

    public TempSignUpDataEntity saveTempSignUpDataEntity(TempSignUpDataEntity tempSignUpDataEntity) {
        return tempSignUpDataRepo.save(tempSignUpDataEntity);
    }

    public void deleteTempSignUpDataEntityByEmail(String email) {
        tempSignUpDataRepo.deleteTempSignUpDataEntityByEmail(email);
    }
}
