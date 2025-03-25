package com.inglo.giggle.security.repository.redis;

import com.inglo.giggle.security.domain.redis.TemporaryToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TemporaryTokenRedisRepository extends CrudRepository<TemporaryToken, String> {
    Optional<TemporaryToken> findById(String compositeKey);
    void deleteById(String compositeKey);

    Optional<TemporaryToken> findByValue(String value);
}
