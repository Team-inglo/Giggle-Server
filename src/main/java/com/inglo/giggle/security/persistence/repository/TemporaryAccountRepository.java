package com.inglo.giggle.security.persistence.repository;

import com.inglo.giggle.security.persistence.entity.redis.TemporaryAccountEntity;

public interface TemporaryAccountRepository {

    TemporaryAccountEntity findByIdOrElseThrow(String id);

    void save(TemporaryAccountEntity temporaryAccountEntity);

    void deleteById(String id);
}
