package com.inglo.giggle.security.repository.redis;

import com.inglo.giggle.security.domain.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByAccountIdAndValue(UUID accountId, String value);
}
