package com.example.cashomat.model.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DepositValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DepositConstraint {
    String message() default "Invalid banknote";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
