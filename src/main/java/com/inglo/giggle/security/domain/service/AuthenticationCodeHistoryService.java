package com.inglo.giggle.security.domain.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.persistence.entity.redis.AuthenticationCodeHistoryEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationCodeHistoryService {
    public AuthenticationCodeHistoryEntity createAuthenticationCodeHistory(String email) {
        return AuthenticationCodeHistoryEntity.builder()
                .email(email)
                .count(1)
                .build();
    }

    public void validateAuthenticationCodeHistory(AuthenticationCodeHistoryEntity history) {
        if (isBlockedIssuingAuthenticationCode(history)) {
            throw new CommonException(ErrorCode.TOO_MANY_AUTHENTICATION_CODE_REQUESTS);
        }
        if (isTooFastIssuingAuthenticationCode(history)) {
            throw new CommonException(ErrorCode.TOO_FAST_AUTHENTICATION_CODE_REQUESTS);
        }
    }

    public AuthenticationCodeHistoryEntity incrementAuthenticationCodeCount(AuthenticationCodeHistoryEntity history) {
        history.incrementCount();
        history.updateLastSentAt();
        return history;
    }

    private Boolean isBlockedIssuingAuthenticationCode(AuthenticationCodeHistoryEntity history) {
        if (history == null) {
            return false;
        }
        return history.getCount() >= 5;
    }

    private Boolean isTooFastIssuingAuthenticationCode(AuthenticationCodeHistoryEntity history) {
        if (history == null) {
            return false;
        }
        return history.getLastSentAt().isAfter(LocalDateTime.now().minusSeconds(10));
    }
}
