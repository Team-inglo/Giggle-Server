package com.inglo.giggle.security.temporarytoken.application.service;

import com.inglo.giggle.security.temporarytoken.application.port.in.command.DeleteTemporaryTokenCommand;
import com.inglo.giggle.security.temporarytoken.application.port.in.usecase.DeleteTemporaryTokenUseCase;
import com.inglo.giggle.security.temporarytoken.application.port.out.DeleteTemporaryTokenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTemporaryTokenService implements DeleteTemporaryTokenUseCase {

    private final DeleteTemporaryTokenPort deleteTemporaryTokenPort;

    @Override
    public void execute(DeleteTemporaryTokenCommand command) {
        deleteTemporaryTokenPort.deleteTemporaryToken(command.getEmail());
    }
}
