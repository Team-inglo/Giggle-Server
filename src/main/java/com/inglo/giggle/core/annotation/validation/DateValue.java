package com.inglo.giggle.core.annotation.validation;

import com.inglo.giggle.core.validator.DateValueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = DateValueValidator.class)
public @interface DateValue {

    String message() default "잘못된 데이터 형식입니다. (yyyy-MM-dd)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
