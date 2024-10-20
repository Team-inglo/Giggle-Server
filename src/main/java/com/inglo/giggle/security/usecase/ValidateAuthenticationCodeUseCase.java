package com.inglo.giggle.security.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.dto.request.ValidateAuthenticationCodeRequestDto;
import com.inglo.giggle.security.dto.response.TemporaryJsonWebTokenDto;

@UseCase
public interface ValidateAuthenticationCodeUseCase {

    /**
     * 이메일 인증 코드 발급
     * @param requestDto 이메일 인증 요청 DTO
     * @return 이메일 인증 응답 DTO
     */
    TemporaryJsonWebTokenDto execute(ValidateAuthenticationCodeRequestDto requestDto);
}
