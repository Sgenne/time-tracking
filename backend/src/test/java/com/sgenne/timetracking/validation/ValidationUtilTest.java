package com.sgenne.timetracking.validation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.sgenne.timetracking.validation.ValidationUtil.isAlphaNumeric;

class ValidationUtilTest {

    @Test
    public void testIsAlphaNumeric() {
        List<String> validStrings = List.of("asdfhjkdfjghksdkjfh", "a", "2", "");
        List<String> invalidStrings = List.of("*", "?", "§", "adf'sddasd", "/", ">", "|", "sfdf4rfd s6uifdg234", " ");

        validStrings.forEach(string -> {
            assert isAlphaNumeric(string);
        });

        invalidStrings.forEach(string -> {
            assert(!isAlphaNumeric(string));
        });


    }

}