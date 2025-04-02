package com.inglo.giggle.security.domain.service;

import com.inglo.giggle.security.persistence.entity.redis.AuthenticationCodeEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationCodeService {
    public AuthenticationCodeEntity createAuthenticationCode(String email, String code) {
        return AuthenticationCodeEntity.builder()
                .email(email)
                .value(code)
                .build();
    }
}
