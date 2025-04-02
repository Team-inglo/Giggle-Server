package com.inglo.giggle.security.persistence.repository.redis;

import com.inglo.giggle.security.persistence.entity.redis.TemporaryAccountEntity;
import org.springframework.data.repository.CrudRepository;

public interface TemporaryAccountRedisRepository extends CrudRepository<TemporaryAccountEntity, String> {
}
