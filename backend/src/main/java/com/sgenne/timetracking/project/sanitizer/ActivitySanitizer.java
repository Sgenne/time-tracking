package com.sgenne.timetracking.project.sanitizer;

public class ActivitySanitizer {

    private ActivitySanitizer() {}

    /**
     * Returns a sanitized version of the given title.
     * @param title The value to sanitize.
     */
    public static String sanitizeTitle(String title) {
        return title.trim();
    }

    /**
     * Returns a sanitized version of the given description.
     * @param description The value to sanitize.
     */
    public static String sanitizeDescription(String description) {
        return description.trim();
    }
}
