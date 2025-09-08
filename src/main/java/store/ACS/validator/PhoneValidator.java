package store.ACS.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<PhoneConstraint, String> {

    @Override
    public void initialize(PhoneConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // hoặc true nếu cho phép null
        }
        // Regex: chỉ cho phép đúng 10 số
        return value.matches("^\\d{10}$");
    }
}
