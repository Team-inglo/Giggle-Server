package com.inglo.giggle.security.temporaryaccount.application.service;

import com.inglo.giggle.security.temporaryaccount.application.port.in.command.DeleteTemporaryAccountCommand;
import com.inglo.giggle.security.temporaryaccount.application.port.in.usecase.DeleteTemporaryAccountUseCase;
import com.inglo.giggle.security.temporaryaccount.application.port.out.DeleteTemporaryAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTemporaryAccountService implements DeleteTemporaryAccountUseCase {

    private final DeleteTemporaryAccountPort deleteTemporaryAccountPort;

    @Override
    public void execute(DeleteTemporaryAccountCommand command) {
        deleteTemporaryAccountPort.deleteTemporaryAccount(command.getEmail());
    }
}
