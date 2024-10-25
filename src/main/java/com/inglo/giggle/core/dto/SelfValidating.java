package com.inglo.giggle.core.dto;

import io.sentry.Sentry;
import jakarta.validation.*;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;

import java.util.Set;

/**
 * SelfValidating 을 상속받아서 사용하는 클래스는
 * 해당 클래스가 만들어질 때 Validation 을 수행한다.
 * @param <T>
 */
public abstract class SelfValidating<T> {

    private final Validator validator;

    public SelfValidating() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Evaluates all Bean Validations on the attributes of this
     * instance.
     */
    protected void validateSelf() {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);
        if (!violations.isEmpty()) {
            Sentry.captureException(new ConstraintViolationException(violations));
            throw new CommonException(ErrorCode.INTERNAL_DATA_ERROR);
        }
    }
}
