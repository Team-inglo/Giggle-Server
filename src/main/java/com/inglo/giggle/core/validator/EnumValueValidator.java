package com.inglo.giggle.core.validator;

import com.inglo.giggle.core.annotation.validation.EnumValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {

    private EnumValue annotation;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Enum<?>[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (enumValues == null) {
            return false;
        }

        for (Object enumValue : enumValues) {
            if (value.toUpperCase().equals(enumValue.toString())) {
                return true;
            }
        }

        return false;
    }
}
