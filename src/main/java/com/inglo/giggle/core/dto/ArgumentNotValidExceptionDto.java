package com.inglo.giggle.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.exception.error.ErrorCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

@Getter
public non-sealed class ArgumentNotValidExceptionDto extends ExceptionDto {

    @JsonProperty("fields")
    private final Map<String, String> fields;

    public ArgumentNotValidExceptionDto(MethodArgumentNotValidException exception) {
        super(ErrorCode.INVALID_ARGUMENT);

        this.fields = new HashMap<>();
        exception.getBindingResult()
                .getAllErrors().forEach(e -> this.fields.put(toSnakeCase(((FieldError) e).getField()), e.getDefaultMessage()));
    }

    public ArgumentNotValidExceptionDto(ConstraintViolationException exception) {
        super(ErrorCode.INVALID_ARGUMENT);

        this.fields = new HashMap<>();

        for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
            fields.put(toSnakeCase(constraintViolation.getPropertyPath().toString()), constraintViolation.getMessage());
        }
    }

    public ArgumentNotValidExceptionDto(HandlerMethodValidationException exception) {
        super(ErrorCode.INVALID_ARGUMENT);

        this.fields = new HashMap<>();

        for (ParameterValidationResult result : exception.getAllValidationResults()) {
            String argumentName = result.getMethodParameter().getParameterName();

            if (argumentName == null) {
                continue;
            }

            fields.put(toSnakeCase(argumentName), result.getResolvableErrors().get(0).getDefaultMessage());
        }
    }

    private String toSnakeCase(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
}
