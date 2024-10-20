package com.inglo.giggle.security.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.security.repository.redis.AuthenticationCodeHistoryRepository;
import com.inglo.giggle.security.repository.redis.AuthenticationCodeRepository;
import com.inglo.giggle.security.repository.redis.TemporaryTokenRepository;
import lombok.RequiredArgsConstructor;
import com.inglo.giggle.security.domain.redis.AuthenticationCode;
import com.inglo.giggle.security.domain.redis.AuthenticationCodeHistory;
import com.inglo.giggle.security.domain.redis.TemporaryToken;
import com.inglo.giggle.security.dto.request.ValidateAuthenticationCodeRequestDto;
import com.inglo.giggle.security.dto.response.TemporaryJsonWebTokenDto;
import com.inglo.giggle.security.usecase.ValidateAuthenticationCodeUseCase;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateAuthenticationCodeService implements ValidateAuthenticationCodeUseCase {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final AuthenticationCodeHistoryRepository authenticationCodeHistoryRepository;
    private final TemporaryTokenRepository temporaryTokenRepository;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Override
    public TemporaryJsonWebTokenDto execute(ValidateAuthenticationCodeRequestDto requestDto) {
        AuthenticationCode authenticationCode = authenticationCodeRepository.findById(requestDto.email())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        AuthenticationCodeHistory authenticationCodeHistory = authenticationCodeHistoryRepository.findById(requestDto.email())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        if (!bCryptPasswordEncoder.matches(requestDto.authenticationCode(), authenticationCode.getValue())) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        authenticationCodeRepository.delete(authenticationCode);
        authenticationCodeHistoryRepository.delete(authenticationCodeHistory);

        TemporaryJsonWebTokenDto temporaryTokenDto = jsonWebTokenUtil.generateTemporaryJsonWebToken(requestDto.email());

        temporaryTokenRepository.save(TemporaryToken.builder()
                .id(requestDto.id())
                .email(requestDto.email())
                .value(temporaryTokenDto.getTemporaryToken())
                .build()
        );

        return temporaryTokenDto;
    }
}
