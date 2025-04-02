package com.inglo.giggle.security.application.service;

import com.inglo.giggle.security.application.usecase.AuthenticateJsonWebTokenUseCase;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.info.CustomUserPrincipal;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticateJsonWebTokenService implements AuthenticateJsonWebTokenUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Override
    public CustomUserPrincipal execute(UUID accountId) {

        Account account = accountRepository.findByIdOrElseThrow(accountId);

        return accountService.createCustomUserPrincipalByAccount(account);
    }
}
