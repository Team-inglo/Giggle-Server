package com.inglo.giggle.security.repository;

import com.inglo.giggle.security.domain.redis.TemporaryAccount;

public interface TemporaryAccountRepository {

    TemporaryAccount findByIdOrElseThrow(String id);

    void save(TemporaryAccount temporaryAccount);

    void deleteById(String id);
}
