package com.trxyzng.trung.authentication.shared.validators;

import com.trxyzng.trung.authentication.shared.constraints.EmailConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {
    public void initialize(EmailConstraint constraintAnnotation) {}
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.equals("")) {
            return false;
        }
        String regex= "^[0-9a-zA-Z@.]{3,255}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
