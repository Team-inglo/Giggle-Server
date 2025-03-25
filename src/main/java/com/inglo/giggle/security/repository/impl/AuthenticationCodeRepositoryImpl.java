package com.inglo.giggle.security.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.redis.AuthenticationCode;
import com.inglo.giggle.security.repository.AuthenticationCodeRepository;
import com.inglo.giggle.security.repository.redis.AuthenticationCodeRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthenticationCodeRepositoryImpl implements AuthenticationCodeRepository {

    private final AuthenticationCodeRedisRepository authenticationCodeRedisRepository;

    @Override
    public AuthenticationCode findByIdOrElseThrow(String id) {
        return authenticationCodeRedisRepository.findById(id).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_AUTHENTICATION_CODE));
    }

    @Override
    public void save(AuthenticationCode authenticationCode) {
        authenticationCodeRedisRepository.save(authenticationCode);
    }

    @Override
    public void delete(AuthenticationCode authenticationCode) {
        authenticationCodeRedisRepository.delete(authenticationCode);
    }
}
