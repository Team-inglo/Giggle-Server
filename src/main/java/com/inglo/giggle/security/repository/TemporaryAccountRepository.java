package com.inglo.giggle.security.repository;

import com.inglo.giggle.security.domain.redis.TemporaryAccount;

public interface TemporaryAccountRepository {

    TemporaryAccount findByIdOrElseNull(String id);

    TemporaryAccount findByIdOrElseThrow(String id);

    void save(TemporaryAccount temporaryAccount);

    TemporaryAccount saveAndReturn(TemporaryAccount temporaryAccount);

    void delete(TemporaryAccount temporaryAccount);

    void deleteById(String id);
}
