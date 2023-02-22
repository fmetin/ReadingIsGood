package com.fmetin.readingisgood.dto;

import com.fmetin.readingisgood.annotation.UniqueEmail;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static com.fmetin.readingisgood.shared.RestResponseMessage.*;

@Data
public class CreateCustomerRequestDto {
    @NotNull(message = MSG_VALIDATION_CONSTRAINT_NAME_NOTNULL)
    @Size(min = 1, max = 255, message = MSG_VALIDATION_CONSTRAINT_NAME_SIZE)
    private String name;
    @NotNull(message = MSG_VALIDATION_CONSTRAINT_SURNAME_NOTNULL)
    @Size(min = 1, max = 255, message = MSG_VALIDATION_CONSTRAINT_SURNAME_SIZE)
    private String surname;

    @NotNull(message = MSG_VALIDATION_CONSTRAINT_EMAIL_NOTNULL)
    @Size(min = 1, max = 255, message = MSG_VALIDATION_CONSTRAINT_EMAIL_SIZE)
    @UniqueEmail
    @Pattern(regexp = "^(.+)@(.+)$", message = MSG_VALIDATION_CONSTRAINT_EMAIL_PATTERN)
    private String email;
    @NotNull(message = MSG_VALIDATION_CONSTRAINT_PASSWORD_NOTNULL)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = MSG_VALIDATION_CONSTRAINT_PASSWORD_PATTERN)
    private String password;
}
