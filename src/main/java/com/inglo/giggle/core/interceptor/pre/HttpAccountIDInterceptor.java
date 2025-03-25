package com.inglo.giggle.core.interceptor.pre;

import com.inglo.giggle.core.constant.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HttpAccountIDInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        // 만약 인증이 되어있지 않은 경우(anonymousUser), 다음으로 진행
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }

        request.setAttribute(Constants.ACCOUNT_ID_ATTRIBUTE_NAME, authentication.getName());

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
