package com.inglo.giggle.security.repository.redis;

import com.inglo.giggle.security.domain.redis.AuthenticationCodeHistory;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticationCodeHistoryRedisRepository extends CrudRepository<AuthenticationCodeHistory, String> {
}
