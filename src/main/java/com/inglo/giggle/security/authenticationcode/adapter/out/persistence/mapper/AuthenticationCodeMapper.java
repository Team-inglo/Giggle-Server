package com.inglo.giggle.security.authenticationcode.adapter.out.persistence.mapper;

import com.inglo.giggle.security.authenticationcode.adapter.out.persistence.entity.AuthenticationCodeEntity;
import com.inglo.giggle.security.authenticationcode.domain.AuthenticationCode;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationCodeMapper {

    public AuthenticationCode toDomain(AuthenticationCodeEntity authenticationCodeEntity) {
        if (authenticationCodeEntity == null) {
            return null;
        }
        return AuthenticationCode.builder()
                .email(authenticationCodeEntity.getEmail())
                .value(authenticationCodeEntity.getValue())
                .build();
    }

    public AuthenticationCodeEntity toEntity(AuthenticationCode authenticationCode) {
        if (authenticationCode == null) {
            return null;
        }
        return AuthenticationCodeEntity.builder()
                .email(authenticationCode.getEmail())
                .value(authenticationCode.getValue())
                .build();
    }
}
