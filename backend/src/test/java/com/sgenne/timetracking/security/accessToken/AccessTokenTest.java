package com.sgenne.timetracking.security.accessToken;

import com.sgenne.timetracking.error.exception.InvalidAccessTokenException;
import com.sgenne.timetracking.error.exception.InvalidCredentialsException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccessTokenTest {

    @Test
    void fromBearerFormat_validInput() {
        List<String> validInputs =
                List.of(
                        "Bearer rmzBtntzG2uqbplbdYWYfulWsEdZJH7vZqr9Ul/RF9oxYFzQdLJqm",
                        "Bearer sdkfjhsdkf",
                        "Bearer  "
                );

        validInputs.forEach(input -> assertDoesNotThrow(
                () -> {
                    AccessToken.fromBearerFormat(input);
                }
        ));
    }

    @Test
    void fromBearerFormat_invalidInput() {
        List<String> invalidInputs = List.of(
                "Bearer",
                "Beare  ",
                "rmzBtntzG2uqbplbdYWYfulWsEdZJH7vZqr9Ul/RF9oxYFzQdLJqm"
        );

        invalidInputs.forEach(input -> assertThrows(InvalidAccessTokenException.class, () -> {
            AccessToken.fromBearerFormat(input);
        }));
    }

}