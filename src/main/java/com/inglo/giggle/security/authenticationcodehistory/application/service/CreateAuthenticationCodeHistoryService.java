package com.inglo.giggle.security.authenticationcodehistory.application.service;

import com.inglo.giggle.security.authenticationcodehistory.application.port.in.command.CreateAuthenticationCodeHistoryCommand;
import com.inglo.giggle.security.authenticationcodehistory.application.port.in.usecase.CreateAuthenticationCodeHistoryUseCase;
import com.inglo.giggle.security.authenticationcodehistory.application.port.out.CreateAuthenticationCodeHistoryPort;
import com.inglo.giggle.security.authenticationcodehistory.domain.AuthenticationCodeHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateAuthenticationCodeHistoryService implements CreateAuthenticationCodeHistoryUseCase {

    private final CreateAuthenticationCodeHistoryPort createAuthenticationCodeHistoryPort;

    @Override
    public void execute(CreateAuthenticationCodeHistoryCommand command) {

        AuthenticationCodeHistory authenticationCodeHistory = AuthenticationCodeHistory.builder()
                .email(command.getEmail())
                .count(1)
                .lastSentAt(LocalDateTime.now())
                .build();

        // 인증 코드 전송 이력 생성
        createAuthenticationCodeHistoryPort.createAuthenticationCodeHistory(authenticationCodeHistory);
    }
}
