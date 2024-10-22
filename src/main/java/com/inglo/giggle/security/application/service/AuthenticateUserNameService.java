package com.inglo.giggle.security.application.service;

import com.inglo.giggle.security.domain.service.AccountDomainService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import com.inglo.giggle.security.application.usecase.AuthenticateUserNameUseCase;
import lombok.RequiredArgsConstructor;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateUserNameService implements AuthenticateUserNameUseCase {

    private final AccountRepository accountRepository;
    private final AccountDomainService accountDomainService;

    @Override
    public UserDetails loadUserByUsername(String serialId) throws UsernameNotFoundException {
        Account account = accountRepository.findBySerialIdAndProvider(serialId, ESecurityProvider.DEFAULT)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with serialId: " + serialId));

        return accountDomainService.createCustomUserPrincipalByAccount(account);
    }
}
