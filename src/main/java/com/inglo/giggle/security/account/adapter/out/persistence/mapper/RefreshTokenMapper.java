package com.inglo.giggle.security.account.adapter.out.persistence.mapper;

import com.inglo.giggle.security.account.adapter.out.persistence.entity.redis.RefreshTokenEntity;
import com.inglo.giggle.security.account.domain.RefreshToken;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {

    public RefreshTokenEntity toEntity(RefreshToken refreshToken) {

        if (refreshToken == null) {
            return null;
        }

        return RefreshTokenEntity.builder()
                .accountId(refreshToken.getAccountId())
                .value(refreshToken.getValue())
                .build();
    }

    public RefreshToken toDomain(RefreshTokenEntity refreshTokenEntity) {

        if (refreshTokenEntity == null) {
            return null;
        }

        return RefreshToken.builder()
                .accountId(refreshTokenEntity.getAccountId())
                .value(refreshTokenEntity.getValue())
                .build();
    }
}
