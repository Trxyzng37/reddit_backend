package com.trxyzng.trung.authentication.changepassword.change_password;

import com.trxyzng.trung.authentication.changepassword.POJO.ChangePassword;
import com.trxyzng.trung.utility.servlet.HttpServletRequestUtils;
import com.trxyzng.trung.utility.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://127.0.0.1:4200")
@RestController
public class ChangePasswordController {
    @Autowired
    ChangePasswordService changePasswordService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public ResponseEntity<String> changePassword(HttpServletRequest request) {
        String body = HttpServletRequestUtils.readRequestBody(request);
        ChangePassword jsonObj = JsonUtils.getObjectFromString(body, ChangePassword.class, ChangePassword::new);
        String email = jsonObj.getEmail();
        String password = jsonObj.getNewPassword();
        String encryptPassword = passwordEncoder.encode(password);
        changePasswordService.updatePasswordForEmail(email, encryptPassword);
        System.out.println("Encrpyt password " + encryptPassword);
        String response = "{\"changePassword\":\"true\"}";
        System.out.println(response);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}
