package com.trxyzng.trung.authentication.signup.usernamepassword.reSendConfirmEmailPasscode;

import com.fasterxml.jackson.databind.JsonNode;
import com.trxyzng.trung.authentication.changepassword.passcode.PasscodeService;
import com.trxyzng.trung.authentication.shared.utility.EmailUtils;
import com.trxyzng.trung.authentication.signup.pojo.IsSignUp;
import com.trxyzng.trung.authentication.signup.usernamepassword.confirmEmail.ConfirmEmailPasscodeService;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@CrossOrigin(origins = "http://127.0.0.1:4200", allowCredentials = "true")
@RestController
public class ReSendConfirmEmailPasscodeController {
    @Autowired
    ConfirmEmailPasscodeService confirmEmailPasscodeService;
    @Autowired
    PasscodeService passcodeService;
    @RequestMapping(value = "/resend-confirm-email-passcode", method = RequestMethod.POST)
    public ResponseEntity<String> reSendConfirmEmailPasscode(@RequestBody String requestBody) {
        JsonNode jsonNode = JsonUtils.getJsonNodeFromString(requestBody);
        String email = JsonUtils.readJsonProperty(jsonNode, "email");
        boolean isConfirmEmailPasscodeByEmailExist = confirmEmailPasscodeService.isConfirmEmailPasscodeByEmailExist(email);
        int passcode = passcodeService.createRandomPasscode();
        System.out.println("Create new passcode: " + passcode);
        if (isConfirmEmailPasscodeByEmailExist) {
            confirmEmailPasscodeService.saveConfirmEmailPasscodeEntity(email, passcode, Instant.now().truncatedTo(ChronoUnit.SECONDS));
        }
        else {
            confirmEmailPasscodeService.updateConfirmEmailPasscodeEntity(email, passcode, Instant.now().truncatedTo(ChronoUnit.SECONDS));
        }
        String emailSubject = "Confirm your sign up";
        String emailBody = "<html><body><p>Thanks for sign up at " + email +
                ". Your pass-code for confirm email is: </p><b style=\"font-size:40px;\">" + passcode + "</b></body></html>";
        EmailUtils.sendEmail(email, emailSubject, emailBody);
        IsSignUp isSignUp = new IsSignUp(true, false, false);
        String responseBody = JsonUtils.getStringFromObject(isSignUp);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
    }
}
