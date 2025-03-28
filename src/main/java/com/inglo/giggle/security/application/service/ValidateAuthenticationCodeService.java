package com.inglo.giggle.security.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.security.application.dto.request.ValidateAuthenticationCodeRequestDto;
import com.inglo.giggle.security.application.dto.response.TemporaryJsonWebTokenDto;
import com.inglo.giggle.security.application.usecase.ValidateAuthenticationCodeUseCase;
import com.inglo.giggle.security.domain.redis.AuthenticationCode;
import com.inglo.giggle.security.domain.redis.AuthenticationCodeHistory;
import com.inglo.giggle.security.domain.service.TemporaryTokenService;
import com.inglo.giggle.security.repository.AuthenticationCodeHistoryRepository;
import com.inglo.giggle.security.repository.AuthenticationCodeRepository;
import com.inglo.giggle.security.repository.TemporaryTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ValidateAuthenticationCodeService implements ValidateAuthenticationCodeUseCase {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final AuthenticationCodeHistoryRepository authenticationCodeHistoryRepository;
    private final TemporaryTokenRepository temporaryTokenRepository;

    private final TemporaryTokenService temporaryTokenService;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Override
    @Transactional
    public TemporaryJsonWebTokenDto execute(ValidateAuthenticationCodeRequestDto requestDto) {
        // 해당 이메일로 발급된 인증코드 조회
        AuthenticationCode authenticationCode = authenticationCodeRepository.findByIdOrElseThrow(requestDto.email());

        // 해당 이메일로 발급된 인증코드 이력 조회
        AuthenticationCodeHistory authenticationCodeHistory = authenticationCodeHistoryRepository.findByIdOrElseThrow(requestDto.email());

        // 인증코드 일치 여부 확인
        if (!bCryptPasswordEncoder.matches(requestDto.authenticationCode(), authenticationCode.getValue())) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        // 인증코드 삭제
        authenticationCodeRepository.delete(authenticationCode);

        // 인증코드 이력 삭제
        authenticationCodeHistoryRepository.delete(authenticationCodeHistory);

        // 임시 토큰 생성
        TemporaryJsonWebTokenDto temporaryTokenDto = jsonWebTokenUtil.generateTemporaryJsonWebToken(requestDto.email());

        // 임시 토큰 저장
        temporaryTokenRepository.save(temporaryTokenService.createTemporaryToken(requestDto.email(), temporaryTokenDto.getTemporaryToken()));

        return temporaryTokenDto;
    }
}
