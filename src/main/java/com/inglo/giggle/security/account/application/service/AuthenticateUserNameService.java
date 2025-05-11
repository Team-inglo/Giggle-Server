package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.security.account.application.port.in.usecase.AuthenticateUserNameUseCase;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.domain.type.ESecurityProvider;
import com.inglo.giggle.security.info.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateUserNameService implements AuthenticateUserNameUseCase {

    private final LoadAccountPort loadAccountPort;

    @Override
    public UserDetails loadUserByUsername(String serialId) throws UsernameNotFoundException {

        Account account = loadAccountPort.loadAccountOrElseThrowUserNameNotFoundException(serialId, ESecurityProvider.DEFAULT);

        return CustomUserPrincipal.create(account);
    }
}
