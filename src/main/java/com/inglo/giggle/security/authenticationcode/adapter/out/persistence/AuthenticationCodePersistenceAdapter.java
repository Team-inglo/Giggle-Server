package com.inglo.giggle.security.authenticationcode.adapter.out.persistence;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.authenticationcode.adapter.out.persistence.mapper.AuthenticationCodeMapper;
import com.inglo.giggle.security.authenticationcode.adapter.out.persistence.repository.redis.AuthenticationCodeRedisRepository;
import com.inglo.giggle.security.authenticationcode.application.port.out.CreateAuthenticationCodePort;
import com.inglo.giggle.security.authenticationcode.application.port.out.DeleteAuthenticationCodePort;
import com.inglo.giggle.security.authenticationcode.application.port.out.LoadAuthenticationCodePort;
import com.inglo.giggle.security.authenticationcode.application.port.out.UpdateAuthenticationCodePort;
import com.inglo.giggle.security.authenticationcode.domain.AuthenticationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthenticationCodePersistenceAdapter implements LoadAuthenticationCodePort, CreateAuthenticationCodePort, UpdateAuthenticationCodePort, DeleteAuthenticationCodePort {

    private final AuthenticationCodeRedisRepository authenticationCodeRedisRepository;
    private final AuthenticationCodeMapper authenticationCodeMapper;

    @Override
    public AuthenticationCode loadAuthenticationCodeOrElseThrow(String id) {
        return authenticationCodeMapper.toDomain(authenticationCodeRedisRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_AUTHENTICATION_CODE)));
    }

    @Override
    public void createAuthenticationCode(AuthenticationCode authenticationCode) {
        authenticationCodeRedisRepository.save(authenticationCodeMapper.toEntity(authenticationCode));
    }

    @Override
    public void updateAuthenticationCode(AuthenticationCode authenticationCode) {
        authenticationCodeRedisRepository.save(authenticationCodeMapper.toEntity(authenticationCode));
    }

    @Override
    public void deleteAuthenticationCode(AuthenticationCode authenticationCode) {
        authenticationCodeRedisRepository.delete(authenticationCodeMapper.toEntity(authenticationCode));
    }
}
