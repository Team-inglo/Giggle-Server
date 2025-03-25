package com.inglo.giggle.security.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.redis.AuthenticationCodeHistory;
import com.inglo.giggle.security.repository.AuthenticationCodeHistoryRepository;
import com.inglo.giggle.security.repository.redis.AuthenticationCodeHistoryRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthenticationCodeHistoryRepositoryImpl implements AuthenticationCodeHistoryRepository {

    private final AuthenticationCodeHistoryRedisRepository authenticationCodeHistoryRedisRepository;

    @Override
    public AuthenticationCodeHistory findByIdOrElseNull(String id) {
        return authenticationCodeHistoryRedisRepository.findById(id).orElse(null);
    }

    @Override
    public AuthenticationCodeHistory findByIdOrElseThrow(String id) {
        return authenticationCodeHistoryRedisRepository.findById(id).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_AUTHENTICATION_CODE_HISTORY));
    }

    @Override
    public AuthenticationCodeHistory saveAndReturn(AuthenticationCodeHistory authenticationCodeHistory) {
        return authenticationCodeHistoryRedisRepository.save(authenticationCodeHistory);
    }

    @Override
    public void delete(AuthenticationCodeHistory authenticationCodeHistory) {
        authenticationCodeHistoryRedisRepository.delete(authenticationCodeHistory);
    }

}
