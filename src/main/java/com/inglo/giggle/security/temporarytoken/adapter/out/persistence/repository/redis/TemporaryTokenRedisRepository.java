package com.inglo.giggle.security.temporarytoken.adapter.out.persistence.repository.redis;

import com.inglo.giggle.security.temporarytoken.adapter.out.persistence.entity.TemporaryTokenEntity;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TemporaryTokenRedisRepository extends CrudRepository<TemporaryTokenEntity, String> {
    @NonNull
    Optional<TemporaryTokenEntity> findById(@NonNull String email);

    void deleteById(@NonNull String email);

    Optional<TemporaryTokenEntity> findByValue(String value);
}
