package com.inglo.giggle.security.domain.service;

import com.inglo.giggle.security.persistence.entity.redis.TemporaryTokenEntity;
import org.springframework.stereotype.Service;

@Service
public class TemporaryTokenService {
    public TemporaryTokenEntity createTemporaryToken(String email, String value) {
        return TemporaryTokenEntity.builder()
                .email(email)
                .value(value)
                .build();
    }
}
