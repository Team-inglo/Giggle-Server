package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.security.account.application.port.in.command.DeleteAccountCommand;
import com.inglo.giggle.security.account.application.port.in.usecase.DeleteAccountUseCase;
import com.inglo.giggle.security.account.application.port.out.DeleteAccountPort;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import com.inglo.giggle.security.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAccountService implements DeleteAccountUseCase {

    private final LoadAccountPort loadAccountPort;
    private final DeleteAccountPort deleteAccountPort;

    @Override
    public void execute(DeleteAccountCommand command) {
        Account account = loadAccountPort.loadAccountWithRefreshTokenOrElseThrow(command.getAccountId());
        deleteAccountPort.deleteAccount(account);
    }
}
