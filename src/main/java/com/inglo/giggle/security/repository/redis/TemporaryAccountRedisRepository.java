package com.inglo.giggle.security.repository.redis;

import com.inglo.giggle.security.domain.redis.TemporaryAccount;
import org.springframework.data.repository.CrudRepository;

public interface TemporaryAccountRedisRepository extends CrudRepository<TemporaryAccount, String> {
}
