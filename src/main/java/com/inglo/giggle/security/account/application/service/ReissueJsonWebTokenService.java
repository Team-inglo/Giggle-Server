package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.core.constant.Constants;
import com.inglo.giggle.core.dto.DefaultJsonWebTokenDto;
import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import com.inglo.giggle.security.account.application.port.in.command.ReissueJsonWebTokenCommand;
import com.inglo.giggle.security.account.application.port.in.result.ReissueJsonWebTokenResult;
import com.inglo.giggle.security.account.application.port.in.usecase.ReissueJsonWebTokenUseCase;
import com.inglo.giggle.security.account.application.port.out.CreateRefreshTokenPort;
import com.inglo.giggle.security.account.application.port.out.LoadRefreshTokenPort;
import com.inglo.giggle.security.account.domain.RefreshToken;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReissueJsonWebTokenService implements ReissueJsonWebTokenUseCase {

    private final LoadRefreshTokenPort loadRefreshTokenPort;
    private final CreateRefreshTokenPort createRefreshTokenPort;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Override
    @Transactional
    public ReissueJsonWebTokenResult execute(ReissueJsonWebTokenCommand command) {

        // Validation
        Claims claims = jsonWebTokenUtil.validateToken(command.getRefreshToken());
        UUID claimsAccountId = UUID.fromString(claims.get(Constants.ACCOUNT_ID_CLAIM_NAME, String.class));
        ESecurityRole role = ESecurityRole.fromString(claims.get(Constants.ACCOUNT_ROLE_CLAIM_NAME, String.class));

        // refresh Token 검증. Redis 에 있는 토큰인지 확인 -> accountId 추출
        RefreshToken refreshToken= loadRefreshTokenPort.loadRefreshToken(claimsAccountId, command.getRefreshToken());

        UUID accountId = refreshToken.getAccountId();

        DefaultJsonWebTokenDto tokens = jsonWebTokenUtil.generateDefaultJsonWebTokens(
                accountId,
                role
        );

        RefreshToken newRefreshToken = RefreshToken.builder()
                .accountId(accountId)
                .value(tokens.getRefreshToken())
                .build();

        // Refresh Token 생성
        createRefreshTokenPort.createRefreshToken(newRefreshToken);

        return ReissueJsonWebTokenResult.of(
                tokens.getAccessToken(),
                tokens.getRefreshToken()
        );
    }
}
