package com.sgenne.timetracking.validation;

import lombok.Data;

import java.util.function.Function;
import java.util.function.Supplier;

@Data
public class ValidationResult {
    private final boolean isValid;
    private final String message;

    public void orThrow(Supplier<RuntimeException> supplier) {
        if (!isValid) throw supplier.get();
    }
    public void orThrow(Function<String, RuntimeException> function) {if (!isValid) throw function.apply(message); }
}
