package com.inglo.giggle.security.authenticationcodehistory.adapter.out.persistence;

import com.inglo.giggle.security.authenticationcodehistory.adapter.out.persistence.mapper.AuthenticationCodeHistoryMapper;
import com.inglo.giggle.security.authenticationcodehistory.adapter.out.persistence.repository.redis.AuthenticationCodeHistoryRedisRepository;
import com.inglo.giggle.security.authenticationcodehistory.application.port.out.CreateAuthenticationCodeHistoryPort;
import com.inglo.giggle.security.authenticationcodehistory.application.port.out.DeleteAuthenticationCodeHistoryPort;
import com.inglo.giggle.security.authenticationcodehistory.application.port.out.LoadAuthenticationCodeHistoryPort;
import com.inglo.giggle.security.authenticationcodehistory.application.port.out.UpdateAuthenticationCodeHistoryPort;
import com.inglo.giggle.security.authenticationcodehistory.domain.AuthenticationCodeHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthenticationCodeHistoryPersistenceAdapter implements LoadAuthenticationCodeHistoryPort, CreateAuthenticationCodeHistoryPort, UpdateAuthenticationCodeHistoryPort, DeleteAuthenticationCodeHistoryPort {

    private final AuthenticationCodeHistoryRedisRepository authenticationCodeHistoryRedisRepository;
    private final AuthenticationCodeHistoryMapper authenticationCodeHistoryMapper;

    @Override
    public AuthenticationCodeHistory loadAuthenticationCodeHistoryOrElseNull(String id) {
        return authenticationCodeHistoryMapper.toDomain(authenticationCodeHistoryRedisRepository.findById(id).orElse(null));
    }

    @Override
    public void createAuthenticationCodeHistory(AuthenticationCodeHistory authenticationCodeHistory) {
        authenticationCodeHistoryRedisRepository.save(authenticationCodeHistoryMapper.toEntity(authenticationCodeHistory));
    }

    @Override
    public void updateAuthenticationCodeHistory(AuthenticationCodeHistory authenticationCodeHistory) {
       authenticationCodeHistoryRedisRepository.save(authenticationCodeHistoryMapper.toEntity(authenticationCodeHistory));
    }

    @Override
    public void deleteAuthenticationCodeHistory(AuthenticationCodeHistory authenticationCodeHistory) {
        authenticationCodeHistoryRedisRepository.delete(authenticationCodeHistoryMapper.toEntity(authenticationCodeHistory));
    }

}
