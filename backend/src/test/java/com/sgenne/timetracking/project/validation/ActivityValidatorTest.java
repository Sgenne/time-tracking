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
        List<Double> validDurations = List.of(0., 1440.);
        List<Double> invalidDurations = List.of(-1., 1441.);


        validDurations.forEach(duration -> {
            assert ActivityValidator.durationIsValid(duration).isValid();
        });

        invalidDurations.forEach(duration -> {
            assert !ActivityValidator.durationIsValid(duration).isValid();
        });

        assert !ActivityValidator.durationIsValid(null).isValid();
    }

    @Test
    void startDateTimeStringIsValid() {
        List<String> validDateTimeStrings = List.of("2022-04-15T17:03:28Z");
        List<String> invalidDateTimeStrings = List.of();

        validDateTimeStrings.forEach(string -> {
            assert ActivityValidator.startDateTimeStringIsValid(string).isValid();
        });

    }
}