package com.inglo.giggle.security.application.service;

import com.inglo.giggle.core.constant.Constants;
import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.presentation.dto.response.DefaultJsonWebTokenDto;
import com.inglo.giggle.security.application.usecase.ReissueJsonWebTokenUseCase;
import com.inglo.giggle.security.persistence.entity.redis.RefreshTokenEntity;
import com.inglo.giggle.security.domain.service.RefreshTokenService;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import com.inglo.giggle.security.persistence.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
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
        // Validation
        Claims claims = jsonWebTokenUtil.validateToken(refreshTokenValue);
        UUID claimsAccountId = UUID.fromString(claims.get(Constants.ACCOUNT_ID_CLAIM_NAME, String.class));

        // refresh Token 검증. Redis에 있는 토큰인지 확인 -> accountId 추출
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByAccountIdAndValueOrElseThrow(claimsAccountId, refreshTokenValue);

        UUID accountId = refreshTokenEntity.getAccountId();

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
