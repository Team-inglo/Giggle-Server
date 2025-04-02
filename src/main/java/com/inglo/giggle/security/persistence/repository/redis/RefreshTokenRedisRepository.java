package com.inglo.giggle.security.persistence.repository.redis;

import com.inglo.giggle.security.persistence.entity.redis.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshTokenEntity, UUID> {
    Optional<RefreshTokenEntity> findByAccountIdAndValue(UUID accountId, String value);
}
