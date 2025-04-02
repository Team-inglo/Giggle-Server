package com.inglo.giggle.security.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.persistence.entity.redis.RefreshTokenEntity;
import com.inglo.giggle.security.persistence.repository.RefreshTokenRepository;
import com.inglo.giggle.security.persistence.repository.redis.RefreshTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public RefreshTokenEntity findByAccountIdAndValueOrElseThrow(UUID accountId, String value) {
        return refreshTokenRedisRepository.findByAccountIdAndValue(accountId, value)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));
    }

    @Override
    public void save(RefreshTokenEntity refreshTokenEntity) {
        refreshTokenRedisRepository.save(refreshTokenEntity);
    }

    @Override
    public void deleteById(UUID id) {
        refreshTokenRedisRepository.deleteById(id);
    }
}
