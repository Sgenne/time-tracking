package com.sgenne.timetracking.validation;

import lombok.Data;

import java.util.function.Function;

@Data
public class ValidationResult {
    private final boolean isValid;
    private final String message;

    /**
     * Returns a ValidationResult indicating a successful validation.
     * @param message The message that will be appended to the ValidationResult
     * @return A valid ValidationResult.
     */
    public static ValidationResult valid(String message) {
        return new ValidationResult(true, message);
    }

    /**
     * Returns a ValidationResult indicating a successful validation.
     * @return A valid ValidationResult.
     */
    public static ValidationResult valid() {
        return new ValidationResult(true, "");
    }

    /**
     * Returns a ValidationResult indicating an unsuccessful validation.
     * @param message A description of why the validation failed.
     * @return An invalid ValidationResult.
     */
    public static ValidationResult inValid(String message) {
        return new ValidationResult(false, message);
    }

    /**
     * Throws a RunTimeException if the validation failed.
     * @param function The function used to produce the Exception.
     */
    public void orThrow(Function<String, RuntimeException> function) {if (!isValid) throw function.apply(message); }
}
