package com.inglo.giggle.security.domain.service;

import com.inglo.giggle.security.domain.redis.AuthenticationCode;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationCodeDomainService {
    public AuthenticationCode createAuthenticationCode(String email, String code) {
        return AuthenticationCode.builder()
                .email(email)
                .value(code)
                .build();
    }
}
