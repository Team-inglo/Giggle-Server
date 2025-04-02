package com.inglo.giggle.security.persistence.repository.redis;

import com.inglo.giggle.security.persistence.entity.redis.AuthenticationCodeHistoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticationCodeHistoryRedisRepository extends CrudRepository<AuthenticationCodeHistoryEntity, String> {
}
