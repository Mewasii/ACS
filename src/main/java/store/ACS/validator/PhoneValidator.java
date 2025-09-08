package store.ACS.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<PhoneConstraint, String> {

    private int must;

    @Override
    public void initialize(PhoneConstraint constraintAnnotation) {
        this.must = constraintAnnotation.must();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false; // để @NotBlank lo message "Phone is required"
        }
        // Regex: chỉ cho phép số và đúng chiều dài must
        return value.matches("^\\d{" + must + "}$");
    }
}
