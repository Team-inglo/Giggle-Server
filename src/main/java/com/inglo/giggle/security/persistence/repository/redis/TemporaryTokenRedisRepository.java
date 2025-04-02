package com.inglo.giggle.security.persistence.repository.redis;

import com.inglo.giggle.security.persistence.entity.redis.TemporaryTokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TemporaryTokenRedisRepository extends CrudRepository<TemporaryTokenEntity, String> {
    Optional<TemporaryTokenEntity> findById(String compositeKey);
    void deleteById(String compositeKey);

    Optional<TemporaryTokenEntity> findByValue(String value);
}
