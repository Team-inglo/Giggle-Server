package com.inglo.giggle.security.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.presentation.dto.request.SignUpDefaultTemporaryRequestDto;
import com.inglo.giggle.security.presentation.dto.response.IssueAuthenticationCodeResponseDto;

@UseCase
public interface SignUpDefaultTemporaryUseCase {
    /**
     * 임시 회원가입 유스케이스
     * @param requestDto 점주 회원가입 요청 DTO With Token
     * @return TemporaryJsonWebTokenDto
     */
     IssueAuthenticationCodeResponseDto execute(SignUpDefaultTemporaryRequestDto requestDto);
}
