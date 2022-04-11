package com.sgenne.timetracking.project.sanitizer;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ActivitySanitizerTest {

    @Test
    void sanitizeTitle() {
        Map<String, String> expectedValues =
                Map.of(" ",
                        "",
                        "a ",
                        "a",
                        " a",
                        "a",
                        "ab",
                        "ab");

        expectedValues.keySet()
                .forEach(sanitizerInput -> {
                    assert ActivitySanitizer
                            .sanitizeTitle(sanitizerInput)
                            .equals(expectedValues
                                    .get(sanitizerInput));
                });
    }
}