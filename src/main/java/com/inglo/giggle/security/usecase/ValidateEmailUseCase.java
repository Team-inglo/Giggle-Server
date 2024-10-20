package com.inglo.giggle.security.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.dto.response.ValidationResponseDto;

@UseCase
public interface ValidateEmailUseCase {

    /**
     * 이메일 인증 코드 발급
     * @param email 이메일 인증 요청 DTO
     * @return 이메일 인증 응답 DTO
     */
    ValidationResponseDto execute(String email);
}
