package com.sgenne.timetracking.project.validation;

import org.junit.jupiter.api.Test;

import java.util.List;

class ActivityValidatorTest {

    @Test
    void titleIsValid() {
        List<String> validStrings = List.of("asd", "hello", "123 123");
        List<String> invalidStrings = List.of("", "   ", "a", "ab");

        validStrings.forEach(validString -> {
            assert ActivityValidator.titleIsValid(validString).isValid();
        });

        invalidStrings.forEach(invalidString -> {
            assert !ActivityValidator.titleIsValid(invalidString).isValid();
        });

        assert !ActivityValidator.titleIsValid(null).isValid();
    }

    @Test
    void descriptionIsValid() {
        List<String> validStrings = List.of("asd", "hello", "123 123", "");

        validStrings.forEach(valid -> {
            assert ActivityValidator.descriptionIsValid(valid).isValid();
        });

        assert !ActivityValidator.descriptionIsValid(null).isValid();
    }

    @Test
    void durationIsValid() {

    }

    @Test
    void startDateTimeStringIsValid() {
    }
}