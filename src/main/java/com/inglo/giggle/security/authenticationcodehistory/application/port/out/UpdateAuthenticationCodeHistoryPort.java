package com.inglo.giggle.security.authenticationcodehistory.application.port.out;

import com.inglo.giggle.security.authenticationcodehistory.domain.AuthenticationCodeHistory;

public interface UpdateAuthenticationCodeHistoryPort {

    void updateAuthenticationCodeHistory(AuthenticationCodeHistory authenticationCodeHistory);

}
