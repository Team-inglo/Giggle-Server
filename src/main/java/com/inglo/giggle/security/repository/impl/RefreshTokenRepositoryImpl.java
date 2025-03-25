package com.inglo.giggle.security.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.redis.RefreshToken;
import com.inglo.giggle.security.repository.RefreshTokenRepository;
import com.inglo.giggle.security.repository.redis.RefreshTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Override
    public RefreshToken findByIdOrElseNull(UUID id) {
        return refreshTokenRedisRepository.findById(id).orElse(null);
    }

    @Override
    public RefreshToken findByIdOrElseThrow(UUID id) {
        return refreshTokenRedisRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_REFRESH_TOKEN));
    }

    @Override
    public RefreshToken findByValueOrElseThrow(String value) {
        return refreshTokenRedisRepository.findByValue(value)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));
    }

    @Override
    public void save(RefreshToken refreshToken) {
        refreshTokenRedisRepository.save(refreshToken);
    }

    @Override
    public RefreshToken saveAndReturn(RefreshToken refreshToken) {
        return refreshTokenRedisRepository.save(refreshToken);
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        refreshTokenRedisRepository.delete(refreshToken);
    }

    @Override
    public void deleteById(UUID id) {
        refreshTokenRedisRepository.deleteById(id);
    }
}
