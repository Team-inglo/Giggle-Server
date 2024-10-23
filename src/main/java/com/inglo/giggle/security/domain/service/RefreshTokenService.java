package com.inglo.giggle.security.domain.service;

import com.inglo.giggle.security.domain.redis.RefreshToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RefreshTokenService {
    public RefreshToken createRefreshToken(UUID accountId, String refreshToken) {
        return RefreshToken.builder()
                .accountId(accountId)
                .value(refreshToken)
                .build();
    }
}
