package com.inglo.giggle.security.application.service;

import com.inglo.giggle.security.presentation.dto.response.DefaultJsonWebTokenDto;
import com.inglo.giggle.security.application.usecase.LoginByDefaultUseCase;
import com.inglo.giggle.security.domain.service.RefreshTokenService;
import com.inglo.giggle.security.info.CustomUserPrincipal;
import com.inglo.giggle.security.persistence.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginDefaultService implements LoginByDefaultUseCase {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    @Override
    @Transactional
    public void execute(CustomUserPrincipal principal, DefaultJsonWebTokenDto jsonWebTokenDto) {
        UUID accountId = principal.getId();
        String refreshToken = jsonWebTokenDto.getRefreshToken();

        if (refreshToken != null) {
            refreshTokenRepository.save(refreshTokenService.createRefreshToken(accountId, refreshToken));
        }
    }
}
