package com.fmetin.readingisgood.annotation.validator;

import com.fmetin.readingisgood.annotation.UniqueEmail;
import com.fmetin.readingisgood.service.CustomerService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final CustomerService customerService;

    @Autowired
    public UniqueEmailValidator(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return customerService.countByEmail(email) <= 0;
    }
}
