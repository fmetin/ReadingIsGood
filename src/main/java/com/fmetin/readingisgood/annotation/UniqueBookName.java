package com.fmetin.readingisgood.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.fmetin.readingisgood.shared.RestResponseMessage.MSG_VALIDATION_CONSTRAINT_UNIQUEBOOKNAME;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {UniqueBookNameValidator.class}
)
public @interface UniqueBookName {

    String message() default MSG_VALIDATION_CONSTRAINT_UNIQUEBOOKNAME;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
