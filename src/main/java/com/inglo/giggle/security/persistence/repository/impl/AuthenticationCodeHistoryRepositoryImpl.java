package com.inglo.giggle.security.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.persistence.entity.redis.AuthenticationCodeHistoryEntity;
import com.inglo.giggle.security.persistence.repository.AuthenticationCodeHistoryRepository;
import com.inglo.giggle.security.persistence.repository.redis.AuthenticationCodeHistoryRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthenticationCodeHistoryRepositoryImpl implements AuthenticationCodeHistoryRepository {

    private final AuthenticationCodeHistoryRedisRepository authenticationCodeHistoryRedisRepository;

    @Override
    public AuthenticationCodeHistoryEntity findByIdOrElseNull(String id) {
        return authenticationCodeHistoryRedisRepository.findById(id).orElse(null);
    }

    @Override
    public AuthenticationCodeHistoryEntity findByIdOrElseThrow(String id) {
        return authenticationCodeHistoryRedisRepository.findById(id).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_AUTHENTICATION_CODE_HISTORY));
    }

    @Override
    public AuthenticationCodeHistoryEntity saveAndReturn(AuthenticationCodeHistoryEntity authenticationCodeHistoryEntity) {
        return authenticationCodeHistoryRedisRepository.save(authenticationCodeHistoryEntity);
    }

    @Override
    public void delete(AuthenticationCodeHistoryEntity authenticationCodeHistoryEntity) {
        authenticationCodeHistoryRedisRepository.delete(authenticationCodeHistoryEntity);
    }

}
