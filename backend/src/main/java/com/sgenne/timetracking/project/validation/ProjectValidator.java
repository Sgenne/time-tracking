package com.sgenne.timetracking.project.validation;


import com.sgenne.timetracking.validation.ValidationResult;
import org.springframework.stereotype.Component;

public class ProjectValidator {

    public static ValidationResult titleIsValid(String title) {
        if (title.length() < 3) {
            return new ValidationResult(
                    false,
                    "The project title must have at least three characters.");
        }
        return new ValidationResult(false, "dongers");
    }





}
