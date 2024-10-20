package com.inglo.giggle.core.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.inglo.giggle.core.contants.Constants;
import com.inglo.giggle.security.dto.response.DefaultJsonWebTokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HttpServletUtil {

    @Value("${web-engine.client-url}")
    private String clientUrl;

    @Value("${web-engine.cookie-domain}")
    private String cookieDomain;

    @Value("${json-web-token.refresh-token-expire-period}")
    private Long refreshTokenExpirePeriod;

    private final ObjectMapper objectMapper;

    public void onSuccessRedirectResponseWithJWTCookie(
            HttpServletResponse response,
            DefaultJsonWebTokenDto tokenDto
    ) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.CREATED.value());

        CookieUtil.addCookie(
                response,
                cookieDomain,
                Constants.ACCESS_TOKEN,
                tokenDto.getAccessToken()
        );
        CookieUtil.addSecureCookie(
                response,
                cookieDomain,
                Constants.REFRESH_TOKEN,
                tokenDto.getRefreshToken(),
                (int) (refreshTokenExpirePeriod / 1000L)
        );

        response.sendRedirect(String.format("%s/%s", clientUrl, "profile"));
    }

    public void onSuccessBodyResponseWithJWTCookie(
            HttpServletResponse response,
            DefaultJsonWebTokenDto tokenDto
    ) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.CREATED.value());

        CookieUtil.addCookie(
                response,
                cookieDomain,
                Constants.ACCESS_TOKEN,
                tokenDto.getAccessToken()
        );
        CookieUtil.addSecureCookie(
                response,
                cookieDomain,
                Constants.REFRESH_TOKEN,
                tokenDto.getRefreshToken(),
                (int) (refreshTokenExpirePeriod / 1000L)
        );

        Map<String, Object> result = new HashMap<>();

        result.put("success", true);
        result.put("data", null);
        result.put("error", null);

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    public void onSuccessBodyResponseWithJWTBody(
            HttpServletResponse response,
            DefaultJsonWebTokenDto tokenDto
    ) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.CREATED.value());

        Map<String, Object> result = new HashMap<>();

        result.put("success", true);
        result.put("data", Map.of(
                        "access_token", tokenDto.getAccessToken(),
                        "refresh_token", tokenDto.getRefreshToken()
                )
        );
        result.put("error", null);

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    public void onSuccessBodyResponse(
            HttpServletResponse response,
            HttpStatus httpStatus
    ) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(httpStatus.value());

        Map<String, Object> result = new HashMap<>();

        if (httpStatus != HttpStatus.NO_CONTENT) {
            result.put("success", true);
            result.put("data", null);
            result.put("error", null);

            response.getWriter().write(objectMapper.writeValueAsString(result));
        }
    }
}
