package com.sgenne.timetracking.project.validation;

import com.sgenne.timetracking.validation.ValidationResult;


public class ProjectValidator {

    /**
     * Checks if a given title is valid. A title is valid if it has
     * between 3 and 16 characters (inclusive) and all characters are
     * alphanumeric.
     * @param title The value to validate.
     * @return A ValidationResult indicating the result of the validation.
     */
    public static ValidationResult titleIsValid(String title) {

        if (title == null) {
            return new ValidationResult(false, "No title was provided.");
        }

        if (title.length() < 3) {
            return new ValidationResult(
                    false,
                    "The project title must have at least three characters."
            );
        }

        return new ValidationResult(true, "");
    }


    /**
     * Checks that a description is provided.
     */
    public static ValidationResult descriptionIsValid(String description) {

        if (description == null) return new ValidationResult(false, "No description was given.");

        return new ValidationResult(true, "");
    }
}
