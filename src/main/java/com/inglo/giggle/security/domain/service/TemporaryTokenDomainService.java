package com.inglo.giggle.security.domain.service;

import com.inglo.giggle.security.domain.redis.TemporaryToken;
import org.springframework.stereotype.Service;

@Service
public class TemporaryTokenDomainService {
    public TemporaryToken createTemporaryToken(String id, String email, String value) {
        return TemporaryToken.builder()
                .id(id)
                .email(email)
                .value(value)
                .build();
    }
}
