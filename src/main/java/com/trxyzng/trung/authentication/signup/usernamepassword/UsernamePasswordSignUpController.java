package com.trxyzng.trung.authentication.signup.usernamepassword;

import com.fasterxml.jackson.databind.JsonNode;
import com.trxyzng.trung.authentication.shared.user.UserEntity;
import com.trxyzng.trung.authentication.shared.user.services.UserEntityService;
import com.trxyzng.trung.authentication.shared.utility.EmailUtils;
import com.trxyzng.trung.authentication.signin.google.OathUserEntity;
import com.trxyzng.trung.authentication.signin.google.OathUserEntityService;
import com.trxyzng.trung.authentication.signup.usernamepassword.confirmEmail.ConfirmEmailPasscodeService;
import com.trxyzng.trung.authentication.signup.pojo.IsSignUp;
import com.trxyzng.trung.authentication.signup.usernamepassword.tempSignupData.TempSignUpDataEntity;
import com.trxyzng.trung.authentication.signup.usernamepassword.tempSignupData.TempSignUpDataService;
import com.trxyzng.trung.utility.EmptyEntityUtils;
import com.trxyzng.trung.utility.JsonUtils;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@CrossOrigin(origins = "http://127.0.0.1:4200", allowCredentials = "true")
@RestController
public class UsernamePasswordSignUpController {
    @Autowired
    private UserEntityService userEntityService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    OathUserEntityService oathUserEntityService;
    @Autowired
    ConfirmEmailPasscodeService confirmEmailPasscodeService;
    @Autowired
    TempSignUpDataService tempSignUpDataService;
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<String> UsernamePasswordSignUp(@RequestBody String body) {
        try {
            JsonNode jsonNode = JsonUtils.getJsonNodeFromString(body);
            String username = JsonUtils.readJsonProperty(jsonNode, "username");
            String password = JsonUtils.readJsonProperty(jsonNode, "password");
            String email = JsonUtils.readJsonProperty(jsonNode, "email");
            String role = JsonUtils.readJsonProperty(jsonNode, "role");
            System.out.println("Body of sign up");
            System.out.println(username);
            System.out.println(password);
            System.out.println(email);
            UserEntity userByUsername = userEntityService.findUserEntityByUsername(username);
            UserEntity userByEmail = userEntityService.findUserEntityByEmail(email);
            OathUserEntity oathUserByEmail = oathUserEntityService.findOathUserEntityByEmail(email);
            boolean isUserByUsernameEmpty = EmptyEntityUtils.isEmptyEntity(userByUsername);
            boolean isUserByEmailEmpty = EmptyEntityUtils.isEmptyEntity(userByEmail) && EmptyEntityUtils.isEmptyEntity(oathUserByEmail);
            HttpHeaders headers = new HttpHeaders();
            if (isUserByUsernameEmpty && isUserByEmailEmpty) {
                System.out.println("No user with name " + username);
                System.out.println("No user with email " + email);
                System.out.println("Sign-up successfully. Encode password and save into database");
                password = passwordEncoder.encode(password);
                System.out.println(password);
                TempSignUpDataEntity tempSignUpDataEntity = new TempSignUpDataEntity(username, password, email);
                tempSignUpDataService.saveTempSignUpDataEntity(tempSignUpDataEntity);
                System.out.println("Save temp signup data");
                IsSignUp isSignUp = new IsSignUp(true, false, false);
                String responseBody = JsonUtils.getStringFromObject(isSignUp);
                System.out.println("body: " + responseBody);
                if (responseBody.equals("")) {
                    System.out.println("Error get string from json");
                    return new ResponseEntity<>("Error get string from json", headers, HttpStatus.BAD_REQUEST);
                }
                else {
                    int passcode = confirmEmailPasscodeService.createRandomPasscode();
                    System.out.println("Create passcode: " + passcode);
                    boolean isEmailWithPasscodeExist = confirmEmailPasscodeService.isConfirmEmailPasscodeByEmailExist(email);
                    if (isEmailWithPasscodeExist) {
                        confirmEmailPasscodeService.updateConfirmEmailPasscodeEntity(email, passcode, Instant.now().truncatedTo(ChronoUnit.SECONDS));
                    }
                    else {
                        confirmEmailPasscodeService.saveConfirmEmailPasscodeEntity(email, passcode, Instant.now().truncatedTo(ChronoUnit.SECONDS));
                    }
                    String emailSubject = "Confirm your sign up";
                    String emailBody = "<html><body><p>Thanks for sign up at " + email +
                            ". Your pass-code for confirm email is: </p><b style=\"font-size:40px;\">" + passcode + "</b></body></html>";
                    EmailUtils.sendEmail(email, emailSubject, emailBody);
                    return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
                }
            }
            else {
                System.out.println(("UserEntity already exist in database"));
                IsSignUp isSignUp = new IsSignUp(false, !isUserByUsernameEmpty, !isUserByEmailEmpty);
                String responseBody = JsonUtils.getStringFromObject(isSignUp);
                if (responseBody.equals(""))
                    return new ResponseEntity<>("Error get string from json", headers, HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
            }
        } catch (ConstraintViolationException e) {
            System.out.println("Constraint error");
            System.out.println(e.getConstraintViolations());
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>("Error: Constraint violation", headers, HttpStatus.BAD_REQUEST);
        }
    }
}
