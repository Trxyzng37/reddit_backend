package com.trxyzng.trung.authentication.signup.usernamepassword.tempSignupData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TempSignUpDataService {
    @Autowired
    TempSignUpDataRepo tempSignUpDataRepo;

    public boolean findTempSignUpDataEntityByEmail(String email) {
        return tempSignUpDataRepo.isDataByEmailExist(email) == 0 ? false : true;
    }

    public TempSignUpDataEntity saveTempSignUpDataEntity(TempSignUpDataEntity tempSignUpDataEntity) {
        return tempSignUpDataRepo.save(tempSignUpDataEntity);
    }

    public void deleteTempSignUpDataEntityByEmail(String email) {
        tempSignUpDataRepo.deleteTempSignUpDataEntityByEmail(email);
    }
}
