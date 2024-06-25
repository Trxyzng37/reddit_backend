package com.trxyzng.trung.authentication.changepassword.change_password;

import com.trxyzng.trung.authentication.changepassword.pojo.ChangePasswordRequest;
import com.trxyzng.trung.authentication.changepassword.pojo.ChangePasswordResponse;
import com.trxyzng.trung.utility.servlet.HttpServletRequestUtils;
import com.trxyzng.trung.utility.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangePasswordController {
    @Autowired
    ChangePasswordService changePasswordService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public ResponseEntity<String> changePassword(HttpServletRequest request) {
        String body = HttpServletRequestUtils.readRequestBody(request);
        ChangePasswordRequest jsonObj = JsonUtils.getObjectFromString(body, ChangePasswordRequest.class, ChangePasswordRequest::new);
        String email = jsonObj.getEmail();
        String newPassword = jsonObj.getNewPassword();
        System.out.println("Email: " + email);
        String encryptNewPassword = passwordEncoder.encode(newPassword);
        System.out.println("New encrpyted password: " + encryptNewPassword);
        String encryptOldPassword = changePasswordService.findOldPasswordByEmail(email);
        System.out.println("Old encrpyted password: " + encryptOldPassword);
        System.out.println("Is password empty: " + encryptOldPassword.equals(""));
        boolean samePassword = passwordEncoder.matches(newPassword, encryptOldPassword);
        System.out.println("Is password same: " + samePassword);
        ChangePasswordResponse changePasswordResponse = new ChangePasswordResponse(!samePassword);
        String responseBody = JsonUtils.getStringFromObject(changePasswordResponse);
        if (responseBody.equals(""))
            return new ResponseEntity<>("error get string from object", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        if (samePassword) {
            System.out.println("Password is the same");
        }
        else {
            System.out.println(("Update password OK"));
            changePasswordService.updatePasswordForEmail(email, encryptNewPassword);
        }
        return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
    }
}
