package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.security.account.application.port.in.command.CreateAccountCommand;
import com.inglo.giggle.security.account.application.port.in.usecase.CreateAccountUseCase;
import com.inglo.giggle.security.account.application.port.out.CreateAccountPort;
import com.inglo.giggle.security.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateAccountService implements CreateAccountUseCase {

    private final CreateAccountPort createAccountPort;

    @Override
    public UUID execute(CreateAccountCommand command) {
        Account account = Account.builder()
                .provider(command.getProvider())
                .role(command.getRole())
                .serialId(command.getSerialId())
                .password(command.getPassword())
                .build();

        account = createAccountPort.createAccount(account);
        return account.getId();
    }
}
