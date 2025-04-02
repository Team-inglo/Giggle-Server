package com.inglo.giggle.security.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.persistence.entity.redis.TemporaryTokenEntity;
import com.inglo.giggle.security.persistence.repository.TemporaryTokenRepository;
import com.inglo.giggle.security.persistence.repository.redis.TemporaryTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TemporaryTokenRepositoryImpl implements TemporaryTokenRepository {

    private final TemporaryTokenRedisRepository temporaryTokenRedisRepository;

    @Override
    public TemporaryTokenEntity findByValueOrElseThrow(String value) {
        return temporaryTokenRedisRepository.findByValue(value).orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));
    }

    @Override
    public void save(TemporaryTokenEntity temporaryTokenEntity) {
        temporaryTokenRedisRepository.save(temporaryTokenEntity);
    }

    @Override
    public void delete(TemporaryTokenEntity temporaryTokenEntity) {
        temporaryTokenRedisRepository.delete(temporaryTokenEntity);
    }

    @Override
    public void deleteById(String compositeKey) {
        temporaryTokenRedisRepository.deleteById(compositeKey);
    }
}
