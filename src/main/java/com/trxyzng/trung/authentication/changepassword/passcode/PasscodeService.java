package com.trxyzng.trung.authentication.changepassword.passcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class PasscodeService {
    @Autowired
    private PasscodeRepo passcodeRepo;
    private static int passcode;
    public int createRandomPasscode() {
        int min = 100000;
        int max = 999999;
        Random random = new Random();
        int newPasscode;
        do {
            newPasscode = random.nextInt((max - min) + 1) + min;
        } while (PasscodeService.passcode == newPasscode);
        PasscodeService.passcode = newPasscode;
        return newPasscode;
    }

    public boolean isEmailWithPasscodeExist(String email) {
        String isEmail = passcodeRepo.findEmailByEmail(email).orElse("");
        return !isEmail.isEmpty();
    }

    public void savePasscodeEntity(String email, int passcode, Instant created_at) {
        PasscodeEntity passcodeEntity = new PasscodeEntity(email, passcode, created_at);
        System.out.println("Save PasscodeEntity");
        passcodeRepo.save(passcodeEntity);
    }

    public void updatePasscodeEntity(String email, int passcode, Instant created_at) {
        System.out.println("Update PasscodeEntity");
        passcodeRepo.updatePasscodeByEmail(email, passcode, created_at);
    }

    public boolean isPasscodeMatch(String email, int passcode) {
        int passcodeInDB = passcodeRepo.findPasscodeByEmail(email);
        return passcode == passcodeInDB;
    }

    /**
     * Check if the time the request is send from the client is between allowed time range
     * @param email - Email of the user
     * @param sendTime - The time the request is send by the client
     * @return boolean
     */
    public boolean isSendTimeValid(String email, Instant sendTime) {
        Instant createdTime = passcodeRepo.findCreatedAtByEmail(email);
        Instant expirationTime = passcodeRepo.findExpirationAtByEmail(email);
        return sendTime.isAfter(createdTime) && sendTime.isBefore(expirationTime);
    }
}
