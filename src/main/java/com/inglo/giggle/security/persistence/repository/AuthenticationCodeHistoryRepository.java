package com.inglo.giggle.security.persistence.repository;

import com.inglo.giggle.security.persistence.entity.redis.AuthenticationCodeHistoryEntity;

public interface AuthenticationCodeHistoryRepository {

    AuthenticationCodeHistoryEntity findByIdOrElseNull(String id);

    AuthenticationCodeHistoryEntity findByIdOrElseThrow(String id);

    AuthenticationCodeHistoryEntity saveAndReturn(AuthenticationCodeHistoryEntity authenticationCodeHistoryEntity);

    void delete(AuthenticationCodeHistoryEntity authenticationCodeHistoryEntity);
}
