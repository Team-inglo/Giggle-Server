package com.inglo.giggle.security.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.security.application.usecase.ValidateAuthenticationCodeUseCase;
import com.inglo.giggle.security.domain.service.TemporaryTokenService;
import com.inglo.giggle.security.repository.redis.AuthenticationCodeHistoryRepository;
import com.inglo.giggle.security.repository.redis.AuthenticationCodeRepository;
import com.inglo.giggle.security.repository.redis.TemporaryTokenRepository;
import lombok.RequiredArgsConstructor;
import com.inglo.giggle.security.domain.redis.AuthenticationCode;
import com.inglo.giggle.security.domain.redis.AuthenticationCodeHistory;
import com.inglo.giggle.security.application.dto.request.ValidateAuthenticationCodeRequestDto;
import com.inglo.giggle.security.application.dto.response.TemporaryJsonWebTokenDto;
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
        AuthenticationCode authenticationCode = authenticationCodeRepository.findById(requestDto.email())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 해당 이메일로 발급된 인증코드 이력 조회
        AuthenticationCodeHistory authenticationCodeHistory = authenticationCodeHistoryRepository.findById(requestDto.email())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

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
        temporaryTokenRepository.save(temporaryTokenService.createTemporaryToken(requestDto.id(), requestDto.email(), temporaryTokenDto.getTemporaryToken()));

        return temporaryTokenDto;
    }
}
