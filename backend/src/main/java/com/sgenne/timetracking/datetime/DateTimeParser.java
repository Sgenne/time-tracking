package com.sgenne.timetracking.datetime;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {

    private DateTimeParser() {}

    private static final DateTimeFormatter zuluDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static LocalDateTime parseZuluDateTime(String zuluDateTimeString) {
        return LocalDateTime.parse(zuluDateTimeString, zuluDateTimeFormatter);
    }

}
