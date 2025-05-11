package com.inglo.giggle.security.temporarytoken.application.service;

import com.inglo.giggle.security.temporarytoken.application.port.in.command.CreateTemporaryTokenCommand;
import com.inglo.giggle.security.temporarytoken.application.port.in.usecase.CreateTemporaryTokenUseCase;
import com.inglo.giggle.security.temporarytoken.application.port.out.CreateTemporaryTokenPort;
import com.inglo.giggle.security.temporarytoken.domain.TemporaryToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateTemporaryTokenService implements CreateTemporaryTokenUseCase {

    private final CreateTemporaryTokenPort createTemporaryTokenPort;

    @Override
    @Transactional
    public void execute(CreateTemporaryTokenCommand command) {

        // 임시 토큰 생성
        TemporaryToken temporaryToken = TemporaryToken.builder()
                .email(command.getEmail())
                .value(command.getTemporaryToken())
                .build();
        createTemporaryTokenPort.createTemporaryToken(temporaryToken);
    }
}
