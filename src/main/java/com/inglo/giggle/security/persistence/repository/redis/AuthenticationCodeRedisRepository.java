package com.inglo.giggle.security.persistence.repository.redis;

import com.inglo.giggle.security.persistence.entity.redis.AuthenticationCodeEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticationCodeRedisRepository extends CrudRepository<AuthenticationCodeEntity, String>{
}
