package com.inglo.giggle.security.temporaryaccount.adapter.out.persistence.repository.redis;

import com.inglo.giggle.security.temporaryaccount.adapter.out.persistence.entity.TemporaryAccountEntity;
import org.springframework.data.repository.CrudRepository;

public interface TemporaryAccountRedisRepository extends CrudRepository<TemporaryAccountEntity, String> {
}
