package com.inglo.giggle.security.authenticationcodehistory.application.port.out;

import com.inglo.giggle.security.authenticationcodehistory.domain.AuthenticationCodeHistory;

public interface CreateAuthenticationCodeHistoryPort {

    void createAuthenticationCodeHistory(AuthenticationCodeHistory authenticationCodeHistory);

}
