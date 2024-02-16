package com.trxyzng.trung.authentication.signup.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.trxyzng.trung.authentication.changepassword.passcode.PasscodeRepo;
import com.trxyzng.trung.authentication.shared.user.UserEntity;
import com.trxyzng.trung.authentication.shared.user.services.UserService;
import com.trxyzng.trung.utility.EmptyEntityUtils;
import com.trxyzng.trung.utility.JsonUtils;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:4200", allowCredentials = "true")
@RestController
public class UsernamePasswordSignUpController {
    @Autowired
    private UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PasscodeRepo passcodeRepo;
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public void signup(@RequestBody String body) {
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
            UserEntity u = userService.loadUserByName(username);
            if (EmptyEntityUtils.isEmptyEntity(u)) {
                System.out.println("No user with name " + username);
                System.out.println("Encode password and save into database");
                password = passwordEncoder.encode(password);
                UserEntity uu = new UserEntity(username, password, email, role);
                userService.SaveUser(uu);
//                passcodeRepo.save(new PasscodeEntity(email, 100000, Instant.now().truncatedTo(ChronoUnit.SECONDS)));
            }
            else {
                System.out.println(("UserEntity already exist in database"));
            }
        }
        catch (JsonProcessingException e) {
            System.out.println("Error read json in sign up controller");
        }
        catch (ConstraintViolationException e) {
            System.out.println("Constraint error");
            System.out.println(e.getConstraintViolations());
        }
        catch (UsernameNotFoundException e) {
            System.out.println("User not found sign up");
        }
    }
}
