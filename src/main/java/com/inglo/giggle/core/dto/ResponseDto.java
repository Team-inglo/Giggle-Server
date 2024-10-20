package com.inglo.giggle.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.exception.type.HttpSecurityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.annotation.Nullable;

@Getter
public class ResponseDto<T> extends SelfValidating<ResponseDto<T>> {

    @JsonIgnore
    private final HttpStatus httpStatus;

    @NotNull
    private final Boolean success;

    @Nullable
    private final T data;

    @Nullable
    private final ExceptionDto error;

    @Builder
    private ResponseDto(
            HttpStatus httpStatus,
            Boolean success,
            @Nullable T data,
            @Nullable ExceptionDto error
    ) {
        this.httpStatus = httpStatus;
        this.success = success;
        this.data = data;
        this.error = error;

        this.validateSelf();
    }

    public static <T> ResponseDto<T> ok(@Nullable final T data) {
        return ResponseDto.<T>builder()
                .httpStatus(HttpStatus.OK)
                .success(true)
                .data(data)
                .error(null)
                .build();
    }

    public static <T> ResponseDto<T> created(@Nullable final T data) {
        return ResponseDto.<T>builder()
                .httpStatus(HttpStatus.CREATED)
                .success(true)
                .data(data)
                .error(null)
                .build();
    }

    public static ResponseDto<Object> noContent() {
        return ResponseDto.<Object>builder()
                .httpStatus(HttpStatus.NO_CONTENT)
                .success(true)
                .data(null)
                .error(null)
                .build();
    }

    public static ResponseDto<Object> fail(final ConstraintViolationException e) {
        return ResponseDto.<Object>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .success(false)
                .data(null)
                .error(new ArgumentNotValidExceptionDto(e))
                .build();
    }

    public static ResponseDto<?> fail(HandlerMethodValidationException e) {
        return ResponseDto.<Object>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .success(false)
                .data(null)
                .error(new ArgumentNotValidExceptionDto(e))
                .build();
    }

    public static ResponseDto<Object> fail(final UnexpectedTypeException e) {
        return ResponseDto.<Object>builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .success(false)
                .data(null)
                .error(ExceptionDto.of(ErrorCode.INVALID_PARAMETER_FORMAT))
                .build();
    }

    public static <T> ResponseDto<T> fail(final MethodArgumentNotValidException e) {
        return ResponseDto.<T>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .success(false)
                .data(null)
                .error(new ArgumentNotValidExceptionDto(e))
                .build();
    }

    public static ResponseDto<Object> fail(final MissingServletRequestParameterException e) {
        return ResponseDto.<Object>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .success(false)
                .data(null)
                .error(ExceptionDto.of(ErrorCode.MISSING_REQUEST_PARAMETER))
                .build();
    }

    public static ResponseDto<Object> fail(final MethodArgumentTypeMismatchException e) {
        return ResponseDto.<Object>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .success(false)
                .data(null)
                .error(ExceptionDto.of(ErrorCode.INVALID_PARAMETER_FORMAT))
                .build();
    }

    public static ResponseDto<Object> fail(final HttpSecurityException e) {
        return ResponseDto.<Object>builder()
                .httpStatus(e.getErrorCode().getHttpStatus())
                .success(false)
                .data(null)
                .error(ExceptionDto.of(e.getErrorCode()))
                .build();
    }

    public static ResponseDto<Object> fail(final CommonException e) {
        return ResponseDto.<Object>builder()
                .httpStatus(e.getErrorCode().getHttpStatus())
                .success(false)
                .data(null)
                .error(ExceptionDto.of(e.getErrorCode()))
                .build();
    }
}
