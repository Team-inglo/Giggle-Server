package com.inglo.giggle.security.persistence.repository;

import com.inglo.giggle.security.persistence.entity.redis.RefreshTokenEntity;

import java.util.UUID;

public interface RefreshTokenRepository {

    RefreshTokenEntity findByAccountIdAndValueOrElseThrow(UUID accountId, String value);

    void save(RefreshTokenEntity refreshTokenEntity);

    void deleteById(UUID id);
}
