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
        int passcode = 0;
        do {
            passcode = random.nextInt((max - min) + 1) + min;
        } while (passcode == 100000);
        return passcode;
    }

    public boolean isPasscodeEntityByEmailExist(String email) {
        PasscodeEntity entity = passcodeRepo.findByEmail(email).orElse(new PasscodeEntity());
        if (EmptyEntityUtils.isEmptyEntity(entity)) {
            return false;
        }
        else {
            return true;
        }
    }
}
