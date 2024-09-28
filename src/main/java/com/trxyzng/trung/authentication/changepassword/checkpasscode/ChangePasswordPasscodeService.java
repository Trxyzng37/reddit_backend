package com.trxyzng.trung.authentication.changepassword.checkpasscode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

    public void savePasscodeEntity(String email, int passcode, Instant created_at) {
        ChangePasswordPasscodeEntity passcodeEntity = new ChangePasswordPasscodeEntity(email, passcode, created_at);
        System.out.println("Save PasscodeEntity");
        changePasswordPasscodeRepo.save(passcodeEntity);
    }

    public void updatePasscodeByEmail(String email, int passcode, Instant created_at) {
        System.out.println("Update PasscodeEntity");
        changePasswordPasscodeRepo.updatePasscodeByEmail(email, passcode, created_at, created_at.plus(3, ChronoUnit.MINUTES));
    }

    public boolean isEmailWithPasscodeExist(String email) {
        String isEmail = changePasswordPasscodeRepo.findEmailByEmail(email).orElse("");
        return !isEmail.isEmpty();
    }
}
