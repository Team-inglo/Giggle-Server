package com.inglo.giggle.security.authenticationcodehistory.adapter.out.persistence.mapper;

import com.inglo.giggle.security.authenticationcodehistory.adapter.out.persistence.entity.AuthenticationCodeHistoryEntity;
import com.inglo.giggle.security.authenticationcodehistory.domain.AuthenticationCodeHistory;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationCodeHistoryMapper {

    public AuthenticationCodeHistory toDomain(AuthenticationCodeHistoryEntity authenticationCodeHistoryEntity) {

        if (authenticationCodeHistoryEntity == null) {
            return null;
        }

        return AuthenticationCodeHistory.builder()
                .email(authenticationCodeHistoryEntity.getEmail())
                .count(authenticationCodeHistoryEntity.getCount())
                .lastSentAt(authenticationCodeHistoryEntity.getLastSentAt())
                .build();
    }

    public AuthenticationCodeHistoryEntity toEntity(AuthenticationCodeHistory authenticationCodeHistory) {

        if (authenticationCodeHistory == null) {
            return null;
        }

        return AuthenticationCodeHistoryEntity.builder()
                .email(authenticationCodeHistory.getEmail())
                .count(authenticationCodeHistory.getCount())
                .lastSentAt(authenticationCodeHistory.getLastSentAt())
                .build();
    }
}
