package com.trxyzng.trung.authentication.changepassword.checkpasscode;

import com.trxyzng.trung.authentication.shared.POJO.PasscodeResponse;
import com.trxyzng.trung.authentication.shared.POJO.Passcode;
import com.trxyzng.trung.authentication.shared.passcode.PasscodeService;
import com.trxyzng.trung.authentication.shared.utility.EmailUtils;
import com.trxyzng.trung.authentication.signup.pojo.ResendEmailPasscodeResponse;
import com.trxyzng.trung.authentication.signup.pojo.ResendPasscodeRequest;
import com.trxyzng.trung.utility.servlet.HttpServletRequestUtils;
import com.trxyzng.trung.utility.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@CrossOrigin(origins = "http://127.0.0.1:4200")
@RestController
public class ChangePasswordPasscodeController {
    @Autowired
    ChangePasswordPasscodeRepo repo;
    @Autowired
    ChangePasswordPasscodeService changePasswordPasscodeService;
    @RequestMapping(value = "/check-passcode", method = RequestMethod.POST)
    public ResponseEntity<String> checkPasscode(HttpServletRequest request) {
        String body = HttpServletRequestUtils.readRequestBody(request);
        Passcode jsonObj = JsonUtils.getObjectFromString(body, Passcode.class, Passcode::new);
        String email = jsonObj.getEmail();
        int passcode = jsonObj.getPasscode();
        Instant sendAt = jsonObj.getSendAt();
        System.out.println(email);
        System.out.println(passcode);
        System.out.println(sendAt);
        boolean isTimeValid = changePasswordPasscodeService.isSendTimeValid(email, sendAt);
        boolean isPasscodeMatch = changePasswordPasscodeService.isPasscodeMatch(email, passcode);
        System.out.println("is passcode: "+isPasscodeMatch);
        PasscodeResponse passcodeResponse = new PasscodeResponse(isPasscodeMatch, isTimeValid);
        String responseBody = JsonUtils.getStringFromObject(passcodeResponse);
        if (responseBody.equals(""))
            return new ResponseEntity<>("error get string from object", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/resend-change-password-passcode", method = RequestMethod.POST)
    public ResponseEntity<String> reSendConfirmEmailPasscode(@RequestBody ResendPasscodeRequest requestBody) {
        try {
            String email = requestBody.getEmail();
            int isEmailExist = repo.isEmaillExist(email);
            int passcode = changePasswordPasscodeService.createRandomPasscode();
            System.out.println("Create new passcode: " + passcode);
            if (isEmailExist == 1) {
                repo.updatePasscodeByEmail(email, passcode, Instant.now().truncatedTo(ChronoUnit.SECONDS));
            }
            else {
                repo.save(new ChangePasswordPasscodeEntity(email, passcode, Instant.now().truncatedTo(ChronoUnit.SECONDS)));
            }
            String emailSubject = "Change password";
            String emailBody = "<html><body><p>Your pass-code is: </p><b style=\"font-size:40px;\">" + passcode + "</b></body></html>";
            EmailUtils.sendEmail(email, emailSubject, emailBody);
            ResendEmailPasscodeResponse response = new ResendEmailPasscodeResponse(true);
            String responseBody = JsonUtils.getStringFromObject(response);
            HttpHeaders headers = new HttpHeaders();
            if (responseBody.equals(""))
                return new ResponseEntity<>("error get string from object", headers, HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(e.toString(), headers, HttpStatus.BAD_REQUEST);
        }
    }
}
