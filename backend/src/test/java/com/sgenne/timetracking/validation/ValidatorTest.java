package com.sgenne.timetracking.validation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ValidatorTest {

    @Test
    public void isEmail_validEmail() {
        List<String> validEmails = List.of("simon.genne@gmail.com", "simongenne@gmail.com");

        validEmails.forEach((email) -> {
            ValidationResult validationResult = Validator.isEmail(email);
            assert(validationResult.isValid());
        });
    }

    @Test
    public void isEmail_invalidEmail() {
        List<String> invalidEmails = List.of("simon.genne.com", "@", "@.gmail.com", "simon.genne@");

        invalidEmails.forEach((email) -> {
            ValidationResult validationResult = Validator.isEmail(email);
            assertFalse(validationResult.isValid());
        });
    }


}