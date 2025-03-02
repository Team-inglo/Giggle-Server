package com.inglo.giggle.core.interceptor.pre;

import com.inglo.giggle.core.constant.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.regex.Pattern;

@Component
public class HttpAccountIDInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        String requestURI = request.getRequestURI();

        // 정규식 기반 예외 처리
        for (String pattern : Constants.REGEX_NO_NEED_AUTH_URLS) {
            if (Pattern.matches(pattern, requestURI)) {
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        request.setAttribute(Constants.ACCOUNT_ID_ATTRIBUTE_NAME, authentication.getName());

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
