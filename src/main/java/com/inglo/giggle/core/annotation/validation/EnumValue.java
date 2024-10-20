package com.inglo.giggle.core.annotation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import com.inglo.giggle.core.validator.EnumValueValidator;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Constraint(validatedBy = EnumValueValidator.class)
public @interface EnumValue {

    String message() default "잘못된 Enum 값입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();
}
