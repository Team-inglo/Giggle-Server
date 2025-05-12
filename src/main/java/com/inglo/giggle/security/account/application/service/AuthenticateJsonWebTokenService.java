package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.security.account.application.port.in.usecase.AuthenticateJsonWebTokenUseCase;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.info.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticateJsonWebTokenService implements AuthenticateJsonWebTokenUseCase {

    private final LoadAccountPort loadAccountPort;

    @Override
    public CustomUserPrincipal execute(UUID accountId) {

        Account account = loadAccountPort.loadAccountWithRefreshTokenOrElseThrow(accountId);

        return CustomUserPrincipal.create(account);
    }
}
