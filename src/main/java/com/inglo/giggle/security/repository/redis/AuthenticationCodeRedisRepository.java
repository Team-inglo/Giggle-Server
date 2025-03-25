package com.inglo.giggle.security.repository.redis;

import com.inglo.giggle.security.domain.redis.AuthenticationCode;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticationCodeRedisRepository extends CrudRepository<AuthenticationCode, String>{
}
