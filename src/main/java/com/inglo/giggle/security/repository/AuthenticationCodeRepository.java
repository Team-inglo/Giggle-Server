package com.inglo.giggle.security.repository;

import com.inglo.giggle.security.domain.redis.AuthenticationCode;

public interface AuthenticationCodeRepository {

    AuthenticationCode findByIdOrElseNull(String id);

    AuthenticationCode findByIdOrElseThrow(String id);

    void save(AuthenticationCode authenticationCode);

    AuthenticationCode saveAndReturn(AuthenticationCode authenticationCode);

    void delete(AuthenticationCode authenticationCode);

    void deleteById(String id);
}
