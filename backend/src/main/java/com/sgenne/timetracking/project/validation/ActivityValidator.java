package com.sgenne.timetracking.project.validation;

import com.sgenne.timetracking.validation.ValidationResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class ActivityValidator {

    /**
     * Checks if an activity title is valid.
     * @param title The value to be validated.
     * @return The result of the validation.
     */
    public static ValidationResult titleIsValid(String title) {
        if (title == null) {
            return ValidationResult
                    .inValid("No title was provided.");
        }

        if (title.length() < 3) {
            return ValidationResult
                    .inValid("The activity title must be at least three characters long.");
        }

        return ValidationResult.valid();
    }

    /**
     * Checks if an activity description is valid.
     * @param description The value to be validated.
     * @return The result of the validation.
     */
    public static ValidationResult descriptionIsValid(String description) {
        if (description == null) {
            return ValidationResult.inValid("No description was provided.");
        }

        return ValidationResult.valid();
    }

    /**
     * Checks if an activity duration is valid.
     * @param duration The value to be validated.
     * @return The result of the validation.
     */
    public static ValidationResult durationIsValid(Double duration) {
        if (duration == null) {
            return ValidationResult.inValid("No duration was provided.");
        }

        if (duration > 1440) {
            return ValidationResult.inValid("An activity cannot be more than 24 hours long.");
        }

        return ValidationResult.valid();
    }

    /**
     * Checks if an activity startDateTime String is valid.
     * @param startDateTime The value to be validated.
     * @return The result of the validation.
     */
    public static ValidationResult startDateTimeStringIsValid(String startDateTime) {
        if (startDateTime == null) {
            return ValidationResult.inValid("No startDateTime was provided.");
        }

        try {
            LocalDateTime.parse(startDateTime);
        } catch (DateTimeParseException e) {
            return ValidationResult
                    .inValid(String.format("%s is not a valid ISO datetime.", startDateTime));
        }

        return ValidationResult.valid();
    }



}




















