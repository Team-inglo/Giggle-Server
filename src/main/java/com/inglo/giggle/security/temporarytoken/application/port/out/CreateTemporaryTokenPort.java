package com.inglo.giggle.security.temporarytoken.application.port.out;

import com.inglo.giggle.security.temporarytoken.domain.TemporaryToken;

public interface CreateTemporaryTokenPort {

    void createTemporaryToken(TemporaryToken temporaryToken);
}
