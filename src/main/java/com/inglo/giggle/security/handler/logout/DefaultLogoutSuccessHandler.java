package com.inglo.giggle.security.handler.logout;

import com.inglo.giggle.core.contants.Constants;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.utility.CookieUtil;
import com.inglo.giggle.core.utility.HttpServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.inglo.giggle.security.handler.common.AbstractFailureHandler;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DefaultLogoutSuccessHandler
        extends AbstractFailureHandler implements LogoutSuccessHandler {

    private final HttpServletUtil httpServletUtil;

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        if (authentication == null) {
            setErrorResponse(response, refineErrorCode(request));
            return;
        }

        // User-Agent 헤더를 통해 요청이 브라우저에서 온 것인지 확인
        String userAgent = request.getHeader("User-Agent");

        // 브라우저에서 온 요청인 경우 쿠키를 삭제함
        if (userAgent != null && userAgent.contains("Mozilla")) {
            CookieUtil.deleteCookie(request, response, Constants.ACCESS_TOKEN);
            CookieUtil.deleteCookie(request, response, Constants.REFRESH_TOKEN);
            CookieUtil.deleteCookie(request, response, "JSESSIONID");
        }

        httpServletUtil.onSuccessBodyResponse(response, HttpStatus.OK);
    }

    private ErrorCode refineErrorCode(HttpServletRequest request) {
        if (request.getAttribute("exception") == null) {
            return ErrorCode.INTERNAL_SERVER_ERROR;
        }

        return (ErrorCode) request.getAttribute("exception");
    }
}
