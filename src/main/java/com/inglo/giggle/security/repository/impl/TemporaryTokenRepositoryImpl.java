package com.inglo.giggle.security.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.redis.TemporaryToken;
import com.inglo.giggle.security.repository.TemporaryTokenRepository;
import com.inglo.giggle.security.repository.redis.TemporaryTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TemporaryTokenRepositoryImpl implements TemporaryTokenRepository {

    private final TemporaryTokenRedisRepository temporaryTokenRedisRepository;

    @Override
    public TemporaryToken findByIdOrElseNull(String compositeKey) {
        return temporaryTokenRedisRepository.findById(compositeKey).orElse(null);
    }

    @Override
    public TemporaryToken findByIdOrElseThrow(String compositeKey) {
        return temporaryTokenRedisRepository.findById(compositeKey).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TEMPORARY_TOKEN));
    }

    @Override
    public TemporaryToken findByValueOrElseThrow(String value) {
        return temporaryTokenRedisRepository.findByValue(value).orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));
    }

    @Override
    public void save(TemporaryToken temporaryToken) {
        temporaryTokenRedisRepository.save(temporaryToken);
    }

    @Override
    public TemporaryToken saveAndReturn(TemporaryToken temporaryToken) {
        return temporaryTokenRedisRepository.save(temporaryToken);
    }

    @Override
    public void delete(TemporaryToken temporaryToken) {
        temporaryTokenRedisRepository.delete(temporaryToken);
    }

    @Override
    public void deleteById(String compositeKey) {
        temporaryTokenRedisRepository.deleteById(compositeKey);
    }
}
