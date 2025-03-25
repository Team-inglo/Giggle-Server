package com.inglo.giggle.security.repository;

import com.inglo.giggle.security.domain.redis.AuthenticationCode;

public interface AuthenticationCodeRepository {

    AuthenticationCode findByIdOrElseThrow(String id);

    void save(AuthenticationCode authenticationCode);

    void delete(AuthenticationCode authenticationCode);
}
