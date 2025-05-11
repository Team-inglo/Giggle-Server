package com.inglo.giggle.security.handler.login;

import com.inglo.giggle.core.dto.DefaultJsonWebTokenDto;
import com.inglo.giggle.core.utility.HttpServletUtil;
import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.security.info.CustomUserPrincipal;
import com.inglo.giggle.security.account.application.port.in.command.LoginDefaultCommand;
import com.inglo.giggle.security.account.application.port.in.usecase.LoginDefaultUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DefaultLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final LoginDefaultUseCase loginDefaultUseCase;

    private final JsonWebTokenUtil jwtUtil;
    private final HttpServletUtil httpServletUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();

        DefaultJsonWebTokenDto jsonWebTokenDto = jwtUtil.generateDefaultJsonWebTokens(
                principal.getId(),
                principal.getRole()
        );

        LoginDefaultCommand command = new LoginDefaultCommand(
                principal,
                jsonWebTokenDto
        );

        loginDefaultUseCase.execute(command);

        httpServletUtil.onSuccessBodyResponseWithJWTBody(response, jsonWebTokenDto);
    }
}
