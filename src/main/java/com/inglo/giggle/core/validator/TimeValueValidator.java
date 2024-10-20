package com.inglo.giggle.core.validator;

import com.inglo.giggle.core.annotation.validation.TimeValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeValueValidator implements ConstraintValidator<TimeValue, String> {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void initialize(TimeValue constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        try {
            LocalTime.parse(value, TIME_FORMATTER);
        } catch(DateTimeParseException e) {
            return false;
        }

        return true;
    }
}
