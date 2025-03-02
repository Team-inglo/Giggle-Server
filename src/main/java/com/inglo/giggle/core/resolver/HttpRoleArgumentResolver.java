package com.inglo.giggle.core.resolver;

import com.inglo.giggle.core.annotation.security.Role;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class HttpRoleArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(ESecurityRole.class)
                && parameter.hasParameterAnnotation(Role.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getAuthorities().isEmpty()) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }
        String authority = authentication.getAuthorities().iterator().next().getAuthority();

        if(authority.startsWith("ROLE_")) {
            authority = authority.substring(5);
        }
        try {
            return ESecurityRole.fromString(authority);
        } catch (IllegalArgumentException ex) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }
    }
}
