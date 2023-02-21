package com.fmetin.readingisgood.annotation;


import com.fmetin.readingisgood.annotation.validator.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.fmetin.readingisgood.shared.RestResponseMessage.MSG_VALIDATION_CONSTRAINT_UNIQUEEMAIL;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {UniqueEmailValidator.class}
)
public @interface UniqueEmail {

    String message() default MSG_VALIDATION_CONSTRAINT_UNIQUEEMAIL;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
