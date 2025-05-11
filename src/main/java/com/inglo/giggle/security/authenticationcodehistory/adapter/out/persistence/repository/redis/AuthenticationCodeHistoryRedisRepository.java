package com.inglo.giggle.security.authenticationcodehistory.adapter.out.persistence.repository.redis;

import com.inglo.giggle.security.authenticationcodehistory.adapter.out.persistence.entity.AuthenticationCodeHistoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticationCodeHistoryRedisRepository extends CrudRepository<AuthenticationCodeHistoryEntity, String> {
}
