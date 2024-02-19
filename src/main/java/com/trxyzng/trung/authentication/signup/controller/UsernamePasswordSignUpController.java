package com.trxyzng.trung.authentication.signup.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.trxyzng.trung.authentication.shared.user.UserEntity;
import com.trxyzng.trung.authentication.shared.user.services.UserEntityService;
import com.trxyzng.trung.authentication.signin.google.OathUserEntity;
import com.trxyzng.trung.authentication.signin.google.OathUserEntityService;
import com.trxyzng.trung.authentication.signup.pojo.IsSignUp;
import com.trxyzng.trung.utility.EmptyEntityUtils;
import com.trxyzng.trung.utility.JsonUtils;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:4200", allowCredentials = "true")
@RestController
public class UsernamePasswordSignUpController {
    @Autowired
    private UserEntityService userEntityService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    OathUserEntityService oathUserEntityService;
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
            boolean isUserByEmailEmpty = EmptyEntityUtils.isEmptyEntity(userByUsername) && EmptyEntityUtils.isEmptyEntity(oathUserByEmail);
            HttpHeaders headers = new HttpHeaders();
            if (isUserByUsernameEmpty && isUserByEmailEmpty) {
                System.out.println("No user with name " + username);
                System.out.println("No user with email " + email);
                System.out.println("Sign-up successfully. Encode password and save into database");
                IsSignUp isSignUp = new IsSignUp(true, false, false);
                String responseBody = JsonUtils.getStringFromObject(isSignUp);
                System.out.println("body: " + responseBody);
                if (responseBody.equals("")) {
                    System.out.println("Error get string from json");
                    return new ResponseEntity<>("Error get string from json", headers, HttpStatus.BAD_REQUEST);
                }
                else {
                    password = passwordEncoder.encode(password);
                    UserEntity uu = new UserEntity(username, password, email, role);
                    userEntityService.SaveUser(uu);
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
