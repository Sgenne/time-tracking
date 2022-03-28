package com.sgenne.timetracking.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

@AllArgsConstructor
public class ValidationResult {
    @Getter
    private final boolean isValid;

    public void orThrow(Supplier<RuntimeException> function) {
        if (!isValid) throw function.get();
    }
}
