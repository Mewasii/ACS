package store.ACS.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PhoneValidator.class) // Gắn với validator
public @interface PhoneConstraint {
    // 3 thuộc tính cơ bản
    String message() default "Invalid phone: ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
