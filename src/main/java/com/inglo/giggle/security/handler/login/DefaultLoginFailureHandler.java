package com.inglo.giggle.security.handler.login;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.security.handler.common.AbstractFailureHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DefaultLoginFailureHandler
        extends AbstractFailureHandler implements AuthenticationFailureHandler {

    private static final String BAD_CREDENTIALS_EXCEPTION = "BadCredentialsException";
    private static final String BAD_CREDENTIALS_MESSAGE = "잘못된 이메일 또는 비밀번호입니다. 다시 입력해주세요.";

    private static final String INTERNAL_AUTHENTICATION_SERVICE_EXCEPTION = "InternalAuthenticationServiceException";
    private static final String INTERNAL_AUTHENTICATION_SERVICE_MESSAGE = "인증 서비스 오류입니다. 관리자에게 문의하세요.";

    private static final String AUTHENTICATION_CREDENTIALS_NOT_FOUND_EXCEPTION = "AuthenticationCredentialsNotFoundException";
    private static final String AUTHENTICATION_CREDENTIALS_NOT_FOUND_MESSAGE = "인증 정보가 없습니다. 관리자에게 문의하세요.";

    private static final String DEFAULT_MESSAGE = "알 수 없는 오류로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";


    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {
        String message = switch (exception.getClass().getSimpleName()) {
            case BAD_CREDENTIALS_EXCEPTION -> BAD_CREDENTIALS_MESSAGE;
            case INTERNAL_AUTHENTICATION_SERVICE_EXCEPTION -> INTERNAL_AUTHENTICATION_SERVICE_MESSAGE;
            case AUTHENTICATION_CREDENTIALS_NOT_FOUND_EXCEPTION -> AUTHENTICATION_CREDENTIALS_NOT_FOUND_MESSAGE;
            default -> DEFAULT_MESSAGE;
        };

        setErrorResponse(
                response,
                message,
                ErrorCode.FAILURE_LOGIN
        );
    }
}
