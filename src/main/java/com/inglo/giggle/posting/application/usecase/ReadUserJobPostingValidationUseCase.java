package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.application.dto.response.ReadUserJobPostingValidationResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserJobPostingValidationUseCase {

    /**
     * 4.8 (유학생) 공고 지원 자격 확인하기
     * @param accountId 계정 ID
     * @param jobPostingId 공고 ID
     */
    ReadUserJobPostingValidationResponseDto execute(
            UUID accountId,
            Long jobPostingId
    ) throws Exception;
}
