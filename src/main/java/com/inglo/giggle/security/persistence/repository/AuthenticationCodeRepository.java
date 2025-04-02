package com.inglo.giggle.security.persistence.repository;

import com.inglo.giggle.security.persistence.entity.redis.AuthenticationCodeEntity;

public interface AuthenticationCodeRepository {

    AuthenticationCodeEntity findByIdOrElseThrow(String id);

    void save(AuthenticationCodeEntity authenticationCodeEntity);

    void delete(AuthenticationCodeEntity authenticationCodeEntity);
}
