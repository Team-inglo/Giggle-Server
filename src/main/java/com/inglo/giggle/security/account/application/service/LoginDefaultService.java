package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.security.account.application.port.in.command.LoginDefaultCommand;
import com.inglo.giggle.security.account.application.port.in.usecase.LoginDefaultUseCase;
import com.inglo.giggle.security.account.application.port.out.CreateRefreshTokenPort;
import com.inglo.giggle.security.account.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginDefaultService implements LoginDefaultUseCase {

    private final CreateRefreshTokenPort createRefreshTokenPort;

    @Override
    @Transactional
    public void execute(LoginDefaultCommand command) {
        UUID accountId = command.getPrincipal().getId();
        String value = command.getDefaultJsonWebTokenDto().getRefreshToken();

        if (value != null) {
            RefreshToken refreshToken = RefreshToken.builder()
                    .accountId(accountId)
                    .value(value)
                    .build();
            createRefreshTokenPort.createRefreshToken(refreshToken);
        }
    }
}
