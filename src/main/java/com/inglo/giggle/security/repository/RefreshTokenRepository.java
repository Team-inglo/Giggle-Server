package com.inglo.giggle.security.repository;

import com.inglo.giggle.security.domain.redis.RefreshToken;

import java.util.UUID;

public interface RefreshTokenRepository {

    RefreshToken findByIdOrElseNull(UUID id);

    RefreshToken findByIdOrElseThrow(UUID id);

    RefreshToken findByValueOrElseThrow(String value);

    void save(RefreshToken refreshToken);

    RefreshToken saveAndReturn(RefreshToken refreshToken);

    void delete(RefreshToken refreshToken);

    void deleteById(UUID id);
}
