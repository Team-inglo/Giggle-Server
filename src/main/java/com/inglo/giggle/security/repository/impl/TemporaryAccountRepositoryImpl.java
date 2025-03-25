package com.inglo.giggle.security.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.redis.TemporaryAccount;
import com.inglo.giggle.security.repository.TemporaryAccountRepository;
import com.inglo.giggle.security.repository.redis.TemporaryAccountRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TemporaryAccountRepositoryImpl implements TemporaryAccountRepository {

    private final TemporaryAccountRedisRepository temporaryAccountRedisRepository;

    @Override
    public TemporaryAccount findByIdOrElseThrow(String id) {
        return temporaryAccountRedisRepository.findById(id).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TEMPORARY_ACCOUNT));
    }

    @Override
    public void save(TemporaryAccount temporaryAccount) {
        temporaryAccountRedisRepository.save(temporaryAccount);
    }

    @Override
    public void deleteById(String id) {
        temporaryAccountRedisRepository.deleteById(id);
    }
}
