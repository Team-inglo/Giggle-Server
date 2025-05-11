package com.inglo.giggle.security.temporarytoken.adapter.out.persistence.mapper;

import com.inglo.giggle.security.temporarytoken.adapter.out.persistence.entity.TemporaryTokenEntity;
import com.inglo.giggle.security.temporarytoken.domain.TemporaryToken;
import org.springframework.stereotype.Component;

@Component
public class TemporaryTokenMapper {

    public TemporaryToken toDomain(TemporaryTokenEntity temporaryTokenEntity) {
        if (temporaryTokenEntity == null) {
            return null;
        }

        return new TemporaryToken(
                temporaryTokenEntity.getEmail(),
                temporaryTokenEntity.getValue()
        );
    }

    public TemporaryTokenEntity toEntity(TemporaryToken temporaryToken) {
        if (temporaryToken == null) {
            return null;
        }

        return new TemporaryTokenEntity(
                temporaryToken.getEmail(),
                temporaryToken.getValue()
        );
    }
}
