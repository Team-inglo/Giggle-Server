package com.inglo.giggle.security.application.service;

import com.inglo.giggle.security.application.usecase.LogoutUseCase;
import com.inglo.giggle.security.info.CustomUserPrincipal;
import com.inglo.giggle.security.repository.AccountDeviceRepository;
import com.inglo.giggle.security.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutUseCase {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AccountDeviceRepository accountDeviceRepository;

    @Override
    @Transactional
    public void execute(CustomUserPrincipal principal) {
        UUID accountId = principal.getId();

        // Refresh Token 삭제
        refreshTokenRepository.deleteById(accountId);

        // AccountDevice 삭제
        accountDeviceRepository.deleteAllByAccountId(accountId);
    }
}
