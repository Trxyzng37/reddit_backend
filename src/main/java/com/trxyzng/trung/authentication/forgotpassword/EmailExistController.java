package com.trxyzng.trung.authentication.forgotpassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:4200")
@RestController
public class EmailExistController {
    @Autowired
    EmailExistService emailExistService;
    @RequestMapping(value = "/check-email", method = RequestMethod.GET)
    public ResponseEntity<String> checkEmail(@RequestParam("email") String email) {
        boolean isEmailExist = emailExistService.isUserByEmailExist(email);
        String response = "{\"email\":\"" + isEmailExist + "\"}";
        System.out.println(response);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
