package com.trxyzng.trung.authentication.signup.usernamepassword.confirmEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class ConfirmEmailPasscodeService {
    @Autowired
    private ConfirmEmailPasscodeRepo confirmEmailPasscodeRepo;
    public int createRandomPasscode() {
        int min = 100000;
        int max = 999999;
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public boolean isConfirmEmailPasscodeByEmailExist(String email) {
        String isEmail = confirmEmailPasscodeRepo.findEmailByEmail(email).orElse("");
        return !isEmail.isEmpty();
    }

    public void saveConfirmEmailPasscodeEntity(String email, int passcode, Instant created_at) {
        ConfirmEmailPasscodeEntity confirmEmailPasscodeEntity = new ConfirmEmailPasscodeEntity(email, passcode, created_at);
        System.out.println("Save ConfirmEmailPasscodeEntity");
        confirmEmailPasscodeRepo.save(confirmEmailPasscodeEntity);
    }

    public void updateConfirmEmailPasscodeEntity(String email, int passcode, Instant created_at) {
        System.out.println("Update ConfirmEmailPasscodeEntity");
        confirmEmailPasscodeRepo.updatePasscodeByEmail(email, passcode, created_at);
    }

    public boolean isPasscodeMatch(String email, int passcode) {
        int passcodeInDB = confirmEmailPasscodeRepo.findPasscodeByEmail(email);
        return passcode == passcodeInDB;
    }

    /**
     * Check if the time the request is send from the client is between allowed time range
     * @param email - Email of the user
     * @param sendTime - The time the request is send by the client
     * @return boolean
     */
    public boolean isSendTimeValid(String email, Instant sendTime) {
        Instant createdTime = confirmEmailPasscodeRepo.findCreatedAtByEmail(email);
        Instant expirationTime = confirmEmailPasscodeRepo.findExpirationAtByEmail(email);
        return sendTime.isAfter(createdTime) && sendTime.isBefore(expirationTime);
    }
}
