package com.inglo.giggle.security.persistence.repository;

import com.inglo.giggle.security.persistence.entity.redis.TemporaryTokenEntity;

public interface TemporaryTokenRepository {

    TemporaryTokenEntity findByValueOrElseThrow(String value);

    void save(TemporaryTokenEntity temporaryTokenEntity);

    void delete(TemporaryTokenEntity temporaryTokenEntity);

    void deleteById(String compositeKey);
}
