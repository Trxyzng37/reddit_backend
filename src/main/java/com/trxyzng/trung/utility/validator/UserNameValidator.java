package com.trxyzng.trung.utility.validator;

import com.trxyzng.trung.utility.constraint.UserNameConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserNameValidator implements ConstraintValidator<UserNameConstraint, String> {
    public void initialize(UserNameConstraint constraintAnnotation) {}
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.equals("")) {
            return false;
        }
        String regex= "^[0-9a-zA-Z]{2,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
