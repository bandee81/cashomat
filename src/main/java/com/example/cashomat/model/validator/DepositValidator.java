package com.example.cashomat.model.validator;

import com.example.cashomat.config.ATMProperties;
import com.example.cashomat.model.Deposit;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

public class DepositValidator implements ConstraintValidator<DepositConstraint, Set<Deposit>> {

    @Autowired
    ATMProperties atmProperties;

    @Override
    public void initialize(DepositConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Set<Deposit> banknotes, ConstraintValidatorContext constraintValidatorContext) {
        if(!atmProperties.getBankNotes()
                .containsAll(banknotes.stream().map(Deposit::banknote).collect(Collectors.toSet()))) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Invalid banknote. Available banknotes: " +
                            atmProperties.getBankNotes())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
