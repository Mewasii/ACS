package store.ACS.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = PhoneValidator.class)
public @interface PhoneConstraint {

    String message() default "Invalid phone number";

    int must() default 10;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
