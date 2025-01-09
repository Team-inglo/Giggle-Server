package com.inglo.giggle.term.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.term.application.dto.request.CreateTermAccountRequestDto;

import java.util.UUID;

@UseCase
public interface CreateTermAccountUseCase {
    /**
     * 사용자 약관 동의하기 유스케이스
     * @param accountId 사용자 ID
     * @param requestDto 사용자 약관 동의 요청 DTO
     */
    void execute(UUID accountId, CreateTermAccountRequestDto requestDto);
}
