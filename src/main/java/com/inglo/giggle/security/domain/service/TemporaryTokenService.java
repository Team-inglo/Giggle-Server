package com.inglo.giggle.security.domain.service;

import com.inglo.giggle.security.domain.redis.TemporaryToken;
import org.springframework.stereotype.Service;

@Service
public class TemporaryTokenService {
    public TemporaryToken createTemporaryToken(String email, String value) {
        return TemporaryToken.builder()
                .email(email)
                .value(value)
                .build();
    }
}
