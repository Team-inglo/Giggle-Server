package com.inglo.giggle.security.domain.service;

import com.inglo.giggle.security.persistence.entity.redis.RefreshTokenEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RefreshTokenService {
    public RefreshTokenEntity createRefreshToken(UUID accountId, String refreshToken) {
        return RefreshTokenEntity.builder()
                .accountId(accountId)
                .value(refreshToken)
                .build();
    }
}
