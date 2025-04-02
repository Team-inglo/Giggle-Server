package com.inglo.giggle.security.application.service;

import com.inglo.giggle.security.application.usecase.AuthenticateUserNameUseCase;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateUserNameService implements AuthenticateUserNameUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String serialId) throws UsernameNotFoundException {

        Account account = accountRepository.findBySerialIdAndProviderOrElseThrowUserNameNotFoundException(serialId, ESecurityProvider.DEFAULT);

        return accountService.createCustomUserPrincipalByAccount(account);
    }
}
