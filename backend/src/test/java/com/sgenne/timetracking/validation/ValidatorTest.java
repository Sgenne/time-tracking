package com.sgenne.timetracking.validation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ValidatorTest {

    @Test
    public void isEmail() {
        List<String> validEmails = List.of("simon.genne@gmail.com", "simongenne@gmail.com");

        validEmails.forEach((email) -> {
            ValidationResult validationResult = Validator.isEmail(email);
            assert(validationResult.isValid());
        });

        List<String> invalidEmails = List.of("simon.genne.com", "@", "@.gmail.com", "simon.genne@");

        invalidEmails.forEach((email) -> {
            ValidationResult validationResult = Validator.isEmail(email);
            assertFalse(validationResult.isValid());
        });
    }

    @Test
    public void hasMinLength() {
        ValidationResult lessThanMinLength = Validator.hasMinLength("aaa", 4);
        ValidationResult exactlyMinLength = Validator.hasMinLength("aaaa", 4);
        ValidationResult moreThanMinLength = Validator.hasMinLength("aaaaa", 4);

        assertFalse(lessThanMinLength.isValid());
        assert(exactlyMinLength.isValid());
        assert(moreThanMinLength.isValid());
    }

    @Test
    public void hasMaxLength() {
        ValidationResult lessThanMaxLength = Validator.hasMaxLength("aaa", 4);
        ValidationResult exactlyMaxLength = Validator.hasMaxLength("aaaa", 4);
        ValidationResult moreThanMaxLength = Validator.hasMaxLength("aaaaa", 4);

        assert(lessThanMaxLength.isValid());
        assert(exactlyMaxLength.isValid());
        assertFalse(moreThanMaxLength.isValid());
    }



}