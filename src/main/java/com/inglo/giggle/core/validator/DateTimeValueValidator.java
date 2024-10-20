package com.inglo.giggle.core.validator;

import com.inglo.giggle.core.annotation.validation.DateTimeValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeValueValidator implements ConstraintValidator<DateTimeValue, String> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void initialize(DateTimeValue constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        try {
            LocalDate.parse(value, DATE_TIME_FORMATTER);
        } catch(DateTimeParseException e) {
            return false;
        }

        return true;
    }
}
