package com.inglo.giggle.security.domain.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.redis.AuthenticationCodeHistory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationCodeHistoryDomainService {
    public AuthenticationCodeHistory createAuthenticationCodeHistory(String email) {
        return AuthenticationCodeHistory.builder()
                .email(email)
                .count(1)
                .build();
    }

    public void validateAuthenticationCodeHistory(AuthenticationCodeHistory history) {
        if (isBlockedIssuingAuthenticationCode(history)) {
            throw new CommonException(ErrorCode.TOO_MANY_AUTHENTICATION_CODE_REQUESTS);
        }
        if (isTooFastIssuingAuthenticationCode(history)) {
            throw new CommonException(ErrorCode.TOO_FAST_AUTHENTICATION_CODE_REQUESTS);
        }
    }

    public AuthenticationCodeHistory incrementAuthenticationCodeCount(AuthenticationCodeHistory history) {
        history.incrementCount();
        history.updateLastSentAt();
        return history;
    }

    private Boolean isBlockedIssuingAuthenticationCode(AuthenticationCodeHistory history) {
        if (history == null) {
            return false;
        }
        return history.getCount() >= 5;
    }

    private Boolean isTooFastIssuingAuthenticationCode(AuthenticationCodeHistory history) {
        if (history == null) {
            return false;
        }
        return history.getLastSentAt().isAfter(LocalDateTime.now().minusMinutes(1));
    }
}
