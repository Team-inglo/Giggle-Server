package com.inglo.giggle.security.application.service;

import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.security.application.usecase.ReissueJsonWebTokenUseCase;
import com.inglo.giggle.security.domain.service.RefreshTokenService;
import com.inglo.giggle.security.repository.AccountRepository;
import com.inglo.giggle.security.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.redis.RefreshToken;
import com.inglo.giggle.security.application.dto.response.DefaultJsonWebTokenDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReissueJsonWebTokenService implements ReissueJsonWebTokenUseCase {

    private final AccountRepository accountRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final RefreshTokenService refreshTokenService;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Override
    @Transactional
    public DefaultJsonWebTokenDto execute(String refreshTokenValue) {
        // refresh Token 검증. Redis에 있는 토큰인지 확인 -> accountId 추출
        RefreshToken refreshToken = refreshTokenRepository.findByValueOrElseThrow(refreshTokenValue);

        UUID accountId = refreshToken.getAccountId();

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // Default Json Web Token 생성
        DefaultJsonWebTokenDto defaultJsonWebTokenDto = jsonWebTokenUtil.generateDefaultJsonWebTokens(
                account.getId(),
                account.getRole()
        );

        // Refresh Token 갱신
        refreshTokenRepository.save(refreshTokenService.createRefreshToken(account.getId(), defaultJsonWebTokenDto.getRefreshToken()));

        return defaultJsonWebTokenDto;
    }
}
