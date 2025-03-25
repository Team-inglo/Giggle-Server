package com.inglo.giggle.security.repository;

import com.inglo.giggle.security.domain.redis.AuthenticationCodeHistory;

public interface AuthenticationCodeHistoryRepository {

    AuthenticationCodeHistory findByIdOrElseNull(String id);

    AuthenticationCodeHistory findByIdOrElseThrow(String id);

    AuthenticationCodeHistory saveAndReturn(AuthenticationCodeHistory authenticationCodeHistory);

    void delete(AuthenticationCodeHistory authenticationCodeHistory);
}
