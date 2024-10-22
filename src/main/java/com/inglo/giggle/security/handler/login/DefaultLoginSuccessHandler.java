package com.inglo.giggle.security.handler.login;

import com.inglo.giggle.core.utility.HttpServletUtil;
import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.security.application.usecase.LoginByDefaultUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.inglo.giggle.security.application.dto.response.DefaultJsonWebTokenDto;
import com.inglo.giggle.security.info.CustomUserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DefaultLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final LoginByDefaultUseCase loginByDefaultUseCase;

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

        loginByDefaultUseCase.execute(principal, jsonWebTokenDto);

        String userAgent = request.getHeader("User-Agent");

        if (userAgent != null && userAgent.contains("Mozilla")) {
            httpServletUtil.onSuccessBodyResponseWithJWTCookie(response, jsonWebTokenDto);
        } else {
            httpServletUtil.onSuccessBodyResponseWithJWTBody(response, jsonWebTokenDto);
        }
    }
}
