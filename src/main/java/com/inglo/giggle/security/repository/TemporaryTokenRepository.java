package com.inglo.giggle.security.repository;

import com.inglo.giggle.security.domain.redis.TemporaryToken;

public interface TemporaryTokenRepository {

    TemporaryToken findByIdOrElseNull(String compositeKey);

    TemporaryToken findByIdOrElseThrow(String compositeKey);

    TemporaryToken findByValueOrElseThrow(String value);

    void save(TemporaryToken temporaryToken);

    TemporaryToken saveAndReturn(TemporaryToken temporaryToken);

    void delete(TemporaryToken temporaryToken);

    void deleteById(String compositeKey);
}
