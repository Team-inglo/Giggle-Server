package com.inglo.giggle.security.account.application.port.out;

import com.inglo.giggle.security.account.domain.RefreshToken;

import java.util.UUID;

public interface LoadRefreshTokenPort {

    RefreshToken loadRefreshToken(UUID accountId, String value);
}
