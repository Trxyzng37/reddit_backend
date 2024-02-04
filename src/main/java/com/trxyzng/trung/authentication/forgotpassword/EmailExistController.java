package com.trxyzng.trung.authentication.forgotpassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@CrossOrigin(origins = "http://127.0.0.1:4200")
@RestController
public class EmailExistController {
    @Autowired
    EmailExistService emailExistService;
    @Autowired
    PasscodeService passcodeService;
    @Autowired
    PasscodeRepo passcodeRepo;
    @RequestMapping(value = "/check-email", method = RequestMethod.GET)
    public ResponseEntity<String> checkEmail(@RequestParam("email") String email) {
        try {
//            boolean isEmailExist = emailExistService.isUserByEmailExist(email);
//            String response = "{\"email\":\"" + isEmailExist + "\"}";
//            System.out.println(response);
            boolean is_passcode = false;
            System.out.println(is_passcode);
            System.out.println("Create passcode and save to database");
            int passcode = passcodeService.createRandomPasscode();
            System.out.println("Update");
            passcodeRepo.updatePasscodeByEmail(email, passcode, Instant.now().truncatedTo(ChronoUnit.SECONDS));
            System.out.println(passcode);
//            if (is_passcode) {
//                System.out.println("Update");
//                passcodeRepo.updatePasscodeByEmail(email, passcode);
//            }
//            else {
//                passcodeRepo.deleteAllByEmail(email);
//                System.out.println("Save");
//                passcodeRepo.save(new PasscodeEntity(email, passcode, Instant.now().truncatedTo(ChronoUnit.SECONDS)));
//            }


//            passcodeRepo.updatePasscodeByEmail(email, passcode);
//            System.out.println(is_exist);
//            passcodeService.savePasscode(email, passcode, Instant.now().truncatedTo(ChronoUnit.SECONDS));
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(String.valueOf(passcode), headers, HttpStatus.OK);
        }
        catch (NumberFormatException e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
