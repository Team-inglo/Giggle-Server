package com.inglo.giggle.security.authenticationcode.application.port.out;

import com.inglo.giggle.security.authenticationcode.domain.AuthenticationCode;

public interface LoadAuthenticationCodePort {

    AuthenticationCode loadAuthenticationCodeOrElseThrow(String id);
}
