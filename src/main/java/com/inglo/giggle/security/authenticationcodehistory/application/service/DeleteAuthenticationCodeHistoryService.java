package com.inglo.giggle.security.authenticationcodehistory.application.service;

import com.inglo.giggle.security.authenticationcodehistory.application.port.in.command.DeleteAuthenticationCodeHistoryCommand;
import com.inglo.giggle.security.authenticationcodehistory.application.port.in.usecase.DeleteAuthenticationCodeHistoryUseCase;
import com.inglo.giggle.security.authenticationcodehistory.application.port.out.DeleteAuthenticationCodeHistoryPort;
import com.inglo.giggle.security.authenticationcodehistory.application.port.out.LoadAuthenticationCodeHistoryPort;
import com.inglo.giggle.security.authenticationcodehistory.domain.AuthenticationCodeHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAuthenticationCodeHistoryService implements DeleteAuthenticationCodeHistoryUseCase {

    private final LoadAuthenticationCodeHistoryPort loadAuthenticationCodeHistoryPort;
    private final DeleteAuthenticationCodeHistoryPort deleteAuthenticationCodeHistoryPort;

    @Override
    public void execute(DeleteAuthenticationCodeHistoryCommand command) {
        AuthenticationCodeHistory authenticationCodeHistory = loadAuthenticationCodeHistoryPort.loadAuthenticationCodeHistoryOrElseNull(command.getEmail());
        if (authenticationCodeHistory == null) {
            return;
        }
        deleteAuthenticationCodeHistoryPort.deleteAuthenticationCodeHistory(authenticationCodeHistory);
    }

}
