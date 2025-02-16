package com.inglo.giggle.security.repository.redis;

import com.inglo.giggle.security.domain.redis.TemporaryAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TemporaryAccountRepository extends CrudRepository<TemporaryAccount, String> {
}
