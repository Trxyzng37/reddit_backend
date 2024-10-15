package com.trxyzng.trung.authentication.signup.usernamepassword;

import com.trxyzng.trung.authentication.shared.user.UserEntityRepo;
import com.trxyzng.trung.authentication.shared.user.services.UserEntityService;
import com.trxyzng.trung.utility.EmailUtils;
import com.trxyzng.trung.authentication.signup.pojo.UsernamePasswordSignUpRequest;
import com.trxyzng.trung.authentication.signup.usernamepassword.confirmEmail.ConfirmEmailPasscodeService;
import com.trxyzng.trung.authentication.signup.pojo.UsernamePasswordSignUpResponse;
import com.trxyzng.trung.authentication.signup.usernamepassword.tempSignupData.TempSignUpDataEntity;
import com.trxyzng.trung.authentication.signup.usernamepassword.tempSignupData.TempSignUpDataRepo;
import com.trxyzng.trung.authentication.signup.usernamepassword.tempSignupData.TempSignUpDataService;
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

@RestController
public class UsernamePasswordSignUpController {
    @Autowired
    private UserEntityRepo userEntityRepo;
    @Autowired
    private UserEntityService userEntityService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ConfirmEmailPasscodeService confirmEmailPasscodeService;
    @Autowired
    TempSignUpDataRepo tempSignUpDataRepo;
    @Autowired
    TempSignUpDataService tempSignUpDataService;
    @RequestMapping(value = "/signup/username-password", method = RequestMethod.POST)
    public ResponseEntity<String> UsernamePasswordSignUp(@RequestBody UsernamePasswordSignUpRequest body) {
        try {
            HttpHeaders headers = new HttpHeaders();
            String username = body.getUsername();
            String password = body.getPassword();
            String email = body.getEmail();
            System.out.println("Body of sign up");
            System.out.println(username);
            System.out.println(password);
            System.out.println(email);
            int isUserByUsernameEmpty = userEntityRepo.existByUsername(username);
            int isUserByEmailEmpty = userEntityRepo.existByEmail(email);
            if (isUserByUsernameEmpty == 0 && isUserByEmailEmpty == 0) {
                System.out.println("No user with name " + username);
                System.out.println("No user with email " + email);
                System.out.println("Sign-up successfully. Encode password and save into database");
                password = passwordEncoder.encode(password);
                System.out.println(password);
                if(tempSignUpDataRepo.isDataByEmailExist(email) == 1)
                    tempSignUpDataRepo.deleteTempSignUpDataEntityByEmail(email);
                if (tempSignUpDataRepo.isDataByUsernameExist(username) == 1)
                    tempSignUpDataRepo.deleteTempSignUpDataEntityByUsername(username);
                TempSignUpDataEntity tempSignUpDataEntity = new TempSignUpDataEntity(username, password, email);
                tempSignUpDataService.saveTempSignUpDataEntity(tempSignUpDataEntity);
                System.out.println("Save temp signup data");
                UsernamePasswordSignUpResponse isSignUp = new UsernamePasswordSignUpResponse(true, 0, 0);
                String responseBody = JsonUtils.getStringFromObject(isSignUp);
                System.out.println("body: " + responseBody);
                if (responseBody.isEmpty()) {
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
                    String emailBody = "<html><body><p>Thanks for sign up at Reddit" +
                            ". Your pass-code for confirm email is: </p><b style=\"font-size:40px;\">" + passcode + "</b></body></html>";
                    EmailUtils.sendEmail(email, emailSubject, emailBody);
                    return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
                }
            }
            else {
                System.out.println("email exist: "+ isUserByEmailEmpty);
                System.out.println("username exist: "+isUserByUsernameEmpty);
                System.out.println(("user already exist in database"));
                UsernamePasswordSignUpResponse isSignUp = new UsernamePasswordSignUpResponse(false, isUserByUsernameEmpty, isUserByEmailEmpty);
                String responseBody = JsonUtils.getStringFromObject(isSignUp);
                if (responseBody.isEmpty())
                    return new ResponseEntity<>("Error get string from json", headers, HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println("Constraint error");
            System.out.println(e.getMessage());
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>("Error: Constraint violation", headers, HttpStatus.BAD_REQUEST);
        }
    }
}
