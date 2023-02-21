package com.fmetin.readingisgood.annotation.validator;

import com.fmetin.readingisgood.annotation.UniqueBookName;
import com.fmetin.readingisgood.service.BookService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueBookNameValidator implements ConstraintValidator<UniqueBookName, String> {
    private final BookService bookService;

    @Autowired
    public UniqueBookNameValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return bookService.countByName(name) <= 0;
    }
}
