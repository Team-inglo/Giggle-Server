package com.inglo.giggle.security.authenticationcodehistory.application.service;

import com.inglo.giggle.security.authenticationcodehistory.application.port.in.command.UpdateAuthenticationCodeHistoryCommand;
import com.inglo.giggle.security.authenticationcodehistory.application.port.in.usecase.UpdateAuthenticationCodeHistoryUseCase;
import com.inglo.giggle.security.authenticationcodehistory.application.port.out.LoadAuthenticationCodeHistoryPort;
import com.inglo.giggle.security.authenticationcodehistory.application.port.out.UpdateAuthenticationCodeHistoryPort;
import com.inglo.giggle.security.authenticationcodehistory.domain.AuthenticationCodeHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateAuthenticationCodeHistoryService implements UpdateAuthenticationCodeHistoryUseCase {

    private final LoadAuthenticationCodeHistoryPort loadAuthenticationCodeHistoryPort;
    private final UpdateAuthenticationCodeHistoryPort updateAuthenticationCodeHistoryPort;

    @Override
    public void execute(UpdateAuthenticationCodeHistoryCommand command) {

        AuthenticationCodeHistory authenticationCodeHistory = loadAuthenticationCodeHistoryPort.loadAuthenticationCodeHistoryOrElseNull(command.getEmail());

        // 인증 코드 전송 이력 업데이트
        authenticationCodeHistory.incrementCount();

        // 인증 코드 전송 이력 업데이트
        updateAuthenticationCodeHistoryPort.updateAuthenticationCodeHistory(authenticationCodeHistory);
    }
}
