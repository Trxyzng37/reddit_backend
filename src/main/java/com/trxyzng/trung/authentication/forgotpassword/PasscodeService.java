package com.trxyzng.trung.authentication.forgotpassword;

import com.trxyzng.trung.utility.EmptyEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class PasscodeService {
    @Autowired
    private PasscodeRepo passcodeRepo;
    public int createRandomPasscode() {
        int min = 100000;
        int max = 999999;
        Random random = new Random();
//        int passcode = 0;
//        do {
//            passcode = random.nextInt((max - min) + 1) + min;
//        } while (passcode == 100000);
        return random.nextInt((max - min) + 1) + min;
    }

    public boolean isEmailWithPasscodeExist(String email) {
        String isEmail = passcodeRepo.findEmail(email).orElse("");
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

    public void saveOrUpdatePasscodeEntity(String email, int passcode, Instant created_at) {
        boolean isEmailWithPasscode = isEmailWithPasscodeExist(email);
        if (isEmailWithPasscode) {
            updatePasscodeEntity(email, passcode, created_at);
        }
        else {
            savePasscodeEntity(email, passcode, created_at);
        }
    }
}
