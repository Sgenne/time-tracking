package com.sgenne.timetracking.project.validation;

import com.sgenne.timetracking.validation.ValidationResult;

import static com.sgenne.timetracking.validation.ValidationUtil.isAlphaNumeric;

public class ProjectValidator {

    /**
     * Checks if a given title is valid. A title is valid if it has
     * between 3 and 16 characters (inclusive) and all characters are
     * alphanumeric.
     * @param title The value to validate.
     * @return A ValidationResult indicating the result of the validation.
     */
    public static ValidationResult titleIsValid(String title) {
        if (title.length() < 3) {
            return new ValidationResult(
                    false,
                    "The project title must have at least three characters."
            );
        }
        if (title.length() > 16) {
            return new ValidationResult(
                    false, "The project title cannot have more than 16 characters."
            );
        }
        if (!isAlphaNumeric(title)) {
            return new ValidationResult(
                    false, "The project title can only have alphanumeric characters."
            );
        }

        return new ValidationResult(true, "");
    }





}
