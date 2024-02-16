package com.trxyzng.trung.authentication.shared.validators;

import com.trxyzng.trung.authentication.shared.constraints.RoleConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<RoleConstraint, String> {
    public void initialize(RoleConstraint constraintAnnotation) {
    }

    public boolean isValid(String role, ConstraintValidatorContext context) {
        if (role == null || role.equals("")) {
            return false;
        }
        String[] allow_role = {"admin", "customer", "seller", "customer_seller"};
        for (int i = 0; i < allow_role.length; i++) {
            if (role.equals(allow_role[i])) {
                return true;
            }
        }
        return false;
    }
}
