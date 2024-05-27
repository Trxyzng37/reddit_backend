package com.trxyzng.trung.authentication.changepassword.checkpasscode;

import com.trxyzng.trung.authentication.signup.usernamepassword.confirmEmail.ConfirmEmailPasscodeEntity;
import com.trxyzng.trung.authentication.signup.usernamepassword.confirmEmail.ConfirmEmailPasscodeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class ChangePasswordPasscodeService {
    @Autowired
    private ChangePasswordPasscodeRepo changePasswordPasscodeRepo;
    public int createRandomPasscode() {
        int min = 100000;
        int max = 999999;
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public boolean isPasscodeMatch(String email, int passcode) {
        int passcodeInDB = changePasswordPasscodeRepo.findPasscodeByEmail(email);
        return passcode == passcodeInDB;
    }

    public boolean isSendTimeValid(String email, Instant sendTime) {
        Instant createdTime = changePasswordPasscodeRepo.findCreatedAtByEmail(email);
        Instant expirationTime = changePasswordPasscodeRepo.findExpirationAtByEmail(email);
        return sendTime.isAfter(createdTime) && sendTime.isBefore(expirationTime);
    }
}
