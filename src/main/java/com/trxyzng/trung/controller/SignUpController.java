package com.trxyzng.trung.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.trxyzng.trung.entity.User;
import com.trxyzng.trung.service.userdetail.UserService;
import com.trxyzng.trung.utility.EmptyEntityUtils;
import com.trxyzng.trung.utility.EmptyObjectUtils;
import com.trxyzng.trung.utility.JsonUtils;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:4200", allowCredentials = "true")
@RestController
public class SignUpController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public void signup(@RequestBody String body) {
        try {
            JsonNode jsonNode = JsonUtils.getJsonObject(body);
            String username = JsonUtils.readJsonProperty(jsonNode, "user");
            String password = JsonUtils.readJsonProperty(jsonNode, "password");
            String email = JsonUtils.readJsonProperty(jsonNode, "email");
            System.out.println("Body of sign up");
            System.out.println(username);
            System.out.println(password);
            System.out.println(email);
            User u = userService.loadUserByName(username);
            if (EmptyEntityUtils.isEmptyEntity(u)) {
                System.out.println("No user with name " + username);
                System.out.println("Save into database");
                User uu = new User(username, password, email);
                userService.SaveUser(uu);
            }
            else {
                System.out.println(("User already exist in database"));
            }
        }
        catch (JsonProcessingException e) {
            System.out.println("Error read json in sign up controller");
        }
        catch (ConstraintViolationException e) {
            System.out.println("Constraint error");
            System.out.println(e.getConstraintViolations());
        }
    };
}
