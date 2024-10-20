package com.inglo.giggle.security.repository.redis;

import com.inglo.giggle.security.domain.redis.TemporaryToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemporaryTokenRepository extends CrudRepository<TemporaryToken, String> {
    Optional<TemporaryToken> findById(String compositeKey);
    void deleteById(String compositeKey);
}
