package com.trxyzng.trung.authentication.changepassword;

import com.fasterxml.jackson.core.JsonProcessingException;
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

@CrossOrigin(origins = "http://127.0.0.1:4200")
@RestController
public class CheckPasscodeController {
    @Autowired
    PasscodeService passcodeService;
    @RequestMapping(value = "/check-passcode", method = RequestMethod.POST)
    public ResponseEntity<String> checkPasscode(HttpServletRequest request) {
        try {
            String body = HttpServletRequestUtils.readRequestBody(request);
            Passcode json = JsonUtils.getJsonObjectFromString(body, Passcode.class);
            System.out.println(json.email);
            System.out.println(json.passcode);
            boolean isPasscodeMatch = passcodeService.isPasscodeMatch(json.email, json.passcode);
            String response = "{\"passcode_match\":\"" + isPasscodeMatch + "\"}";
            System.out.println(response);
            if (isPasscodeMatch) {
                return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
            }
        }
        catch (JsonProcessingException e) {
            return new ResponseEntity<>("error parsing json object", new HttpHeaders(), HttpStatus.OK);
        }
    }
}
