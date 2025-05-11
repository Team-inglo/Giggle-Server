package com.inglo.giggle.security.account.application.port.in.usecase;

import com.inglo.giggle.security.info.CustomUserPrincipal;

import java.util.UUID;

public interface AuthenticateJsonWebTokenUseCase {

    CustomUserPrincipal execute(UUID accountId);
}
