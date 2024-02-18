package com.trxyzng.trung.authentication.changepassword.passcode;

import com.trxyzng.trung.authentication.changepassword.POJO.Passcode;
import com.trxyzng.trung.utility.HttpServletRequestUtils;
import com.trxyzng.trung.utility.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@CrossOrigin(origins = "http://127.0.0.1:4200")
@RestController
public class CheckPasscodeController {
    @Autowired
    PasscodeService passcodeService;
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
        boolean isTimeValid = passcodeService.isSendTimeValid(email, sendAt);
        if (isTimeValid) {
            boolean isPasscodeMatch = passcodeService.isPasscodeMatch(email, passcode);
            String response = "{\"passcode_match\":\"" + isPasscodeMatch + "\"}";
            System.out.println(response);
            if (isPasscodeMatch) {
                return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
            }
        }
        else {
            return new ResponseEntity<>("{\"passcode_match\":\"false\"}", new HttpHeaders(), HttpStatus.OK);
        }

    }
}
