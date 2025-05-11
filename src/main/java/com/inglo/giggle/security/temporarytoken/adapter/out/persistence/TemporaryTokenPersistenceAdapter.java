package com.inglo.giggle.security.temporarytoken.adapter.out.persistence;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.temporarytoken.adapter.out.persistence.mapper.TemporaryTokenMapper;
import com.inglo.giggle.security.temporarytoken.adapter.out.persistence.repository.redis.TemporaryTokenRedisRepository;
import com.inglo.giggle.security.temporarytoken.application.port.out.CreateTemporaryTokenPort;
import com.inglo.giggle.security.temporarytoken.application.port.out.DeleteTemporaryTokenPort;
import com.inglo.giggle.security.temporarytoken.application.port.out.LoadTemporaryTokenPort;
import com.inglo.giggle.security.temporarytoken.domain.TemporaryToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TemporaryTokenPersistenceAdapter implements LoadTemporaryTokenPort, CreateTemporaryTokenPort, DeleteTemporaryTokenPort {

    private final TemporaryTokenRedisRepository temporaryTokenRedisRepository;
    private final TemporaryTokenMapper temporaryTokenMapper;

    @Override
    public TemporaryToken loadTemporaryToken(String value) {
        return temporaryTokenMapper.toDomain(temporaryTokenRedisRepository.findByValue(value).orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR)));
    }

    @Override
    public void createTemporaryToken(TemporaryToken temporaryToken) {
        temporaryTokenRedisRepository.save(temporaryTokenMapper.toEntity(temporaryToken));
    }

    @Override
    public void deleteTemporaryToken(String email) {
        temporaryTokenRedisRepository.deleteById(email);
    }
}
