package com.inglo.giggle.security.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.persistence.entity.redis.TemporaryAccountEntity;
import com.inglo.giggle.security.persistence.repository.TemporaryAccountRepository;
import com.inglo.giggle.security.persistence.repository.redis.TemporaryAccountRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TemporaryAccountRepositoryImpl implements TemporaryAccountRepository {

    private final TemporaryAccountRedisRepository temporaryAccountRedisRepository;

    @Override
    public TemporaryAccountEntity findByIdOrElseThrow(String id) {
        return temporaryAccountRedisRepository.findById(id).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TEMPORARY_ACCOUNT));
    }

    @Override
    public void save(TemporaryAccountEntity temporaryAccountEntity) {
        temporaryAccountRedisRepository.save(temporaryAccountEntity);
    }

    @Override
    public void deleteById(String id) {
        temporaryAccountRedisRepository.deleteById(id);
    }
}
