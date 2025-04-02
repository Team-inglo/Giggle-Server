package com.inglo.giggle.security.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.persistence.entity.redis.AuthenticationCodeEntity;
import com.inglo.giggle.security.persistence.repository.AuthenticationCodeRepository;
import com.inglo.giggle.security.persistence.repository.redis.AuthenticationCodeRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthenticationCodeRepositoryImpl implements AuthenticationCodeRepository {

    private final AuthenticationCodeRedisRepository authenticationCodeRedisRepository;

    @Override
    public AuthenticationCodeEntity findByIdOrElseThrow(String id) {
        return authenticationCodeRedisRepository.findById(id).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_AUTHENTICATION_CODE));
    }

    @Override
    public void save(AuthenticationCodeEntity authenticationCodeEntity) {
        authenticationCodeRedisRepository.save(authenticationCodeEntity);
    }

    @Override
    public void delete(AuthenticationCodeEntity authenticationCodeEntity) {
        authenticationCodeRedisRepository.delete(authenticationCodeEntity);
    }
}
