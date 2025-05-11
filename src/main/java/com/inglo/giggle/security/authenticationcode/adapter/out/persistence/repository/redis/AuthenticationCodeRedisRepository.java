package com.inglo.giggle.security.authenticationcode.adapter.out.persistence.repository.redis;

import com.inglo.giggle.security.authenticationcode.adapter.out.persistence.entity.AuthenticationCodeEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticationCodeRedisRepository extends CrudRepository<AuthenticationCodeEntity, String>{
}
