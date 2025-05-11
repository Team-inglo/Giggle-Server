package com.inglo.giggle.security.account.application.port.out;

import com.inglo.giggle.security.account.domain.RefreshToken;

public interface UpdateRefreshTokenPort {

    void updateRefreshToken(RefreshToken refreshToken);
}
