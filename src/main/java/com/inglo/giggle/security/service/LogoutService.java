package com.inglo.giggle.security.service;

import com.inglo.giggle.security.repository.redis.RefreshTokenRepository;
import com.inglo.giggle.security.usecase.LogoutUseCase;
import lombok.RequiredArgsConstructor;
import com.inglo.giggle.security.info.CustomUserPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutUseCase {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void execute(CustomUserPrincipal principal) {
        UUID accountId = principal.getId();

        refreshTokenRepository.deleteById(accountId);
    }
}
