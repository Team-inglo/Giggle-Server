package com.inglo.giggle.security.repository;

import com.inglo.giggle.security.domain.redis.RefreshToken;

import java.util.UUID;

public interface RefreshTokenRepository {

    RefreshToken findByValueOrElseThrow(String value);

    void save(RefreshToken refreshToken);

    void deleteById(UUID id);
}
