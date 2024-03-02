package com.trxyzng.trung.authentication.changepassword.email_exist;

import com.trxyzng.trung.authentication.changepassword.POJO.EmailExistResponse;
import com.trxyzng.trung.authentication.shared.passcode.PasscodeService;
import com.trxyzng.trung.authentication.shared.utility.EmailUtils;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@CrossOrigin(origins = "http://trxyzng.up.railway.app")
@RestController
public class EmailExistController {
    @Autowired
    EmailExistService emailExistService;
    @Autowired
    PasscodeService passcodeService;
    @RequestMapping(value = "/is-email-exist", method = RequestMethod.GET)
    public ResponseEntity<String> checkEmail(@RequestParam("email") String email) {
        System.out.println(email);
        boolean isUserEntityByEmailExist = emailExistService.isUserEntityByEmailExist(email);
        EmailExistResponse emailExistResponse = new EmailExistResponse(isUserEntityByEmailExist);
        String responseBody = JsonUtils.getStringFromObject(emailExistResponse);
        if (responseBody.equals(""))
            return new ResponseEntity<>("error parsing string from object", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        if (isUserEntityByEmailExist) {
            System.out.println("User with this email exist");
            int passcode = passcodeService.createRandomPasscode();
            System.out.println("Create passcode: " + passcode);
            boolean isEmailWithPasscodeExist = passcodeService.isEmailWithPasscodeExist(email);
            if (isEmailWithPasscodeExist) {
                passcodeService.updatePasscodeEntity(email, passcode, Instant.now().truncatedTo(ChronoUnit.SECONDS));
            }
            else {
                passcodeService.savePasscodeEntity(email, passcode, Instant.now().truncatedTo(ChronoUnit.SECONDS));
            }
            String emailSubject = "Your pass-code";
            String emailBody = "<html><body><p>Your pass-code is: </p><b style=\"font-size:40px;\">" + passcode + "</b></body></html>";
            EmailUtils.sendEmail(email, emailSubject, emailBody);
        }
        else {
            System.out.println("No user linked to this email");
        }
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }
}
