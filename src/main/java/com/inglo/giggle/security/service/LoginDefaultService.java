package com.inglo.giggle.security.service;

import com.inglo.giggle.security.repository.redis.RefreshTokenRepository;
import com.inglo.giggle.security.usecase.LoginByDefaultUseCase;
import lombok.RequiredArgsConstructor;
import com.inglo.giggle.security.domain.redis.RefreshToken;
import com.inglo.giggle.security.dto.response.DefaultJsonWebTokenDto;
import com.inglo.giggle.security.info.CustomUserPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginDefaultService implements LoginByDefaultUseCase {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void execute(CustomUserPrincipal principal, DefaultJsonWebTokenDto jsonWebTokenDto) {
        UUID userId = principal.getId();
        String refreshToken = jsonWebTokenDto.getRefreshToken();

        if (refreshToken != null) {
            refreshTokenRepository.save(
                    RefreshToken.builder()
                            .accountId(userId)
                            .value(refreshToken)
                            .build()
            );
        }
    }
}
