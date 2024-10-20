package com.inglo.giggle.security.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.dto.request.IssueAuthenticationCodeRequestDto;
import com.inglo.giggle.security.dto.response.IssueAuthenticationCodeResponseDto;

@UseCase
public interface ReissueAuthenticationCodeUseCase {
    /**
     * 이메일 인증 코드 발급
     * @param requestDto 이메일 인증 요청 DTO
     * @return 이메일 인증 응답 DTO
     */
    IssueAuthenticationCodeResponseDto execute(IssueAuthenticationCodeRequestDto requestDto);
}