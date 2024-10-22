package com.inglo.giggle.security.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.service.AccountDomainService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import com.inglo.giggle.security.application.usecase.AuthenticateJsonWebTokenUseCase;
import lombok.RequiredArgsConstructor;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.info.CustomUserPrincipal;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticateJsonWebTokenService implements AuthenticateJsonWebTokenUseCase {

    private final AccountRepository accountRepository;
    private final AccountDomainService accountDomainService;

    @Override
    public CustomUserPrincipal execute(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));

        return accountDomainService.createCustomUserPrincipalByAccount(account);
    }
}
