package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.presentation.dto.response.ReadAdminJobPostingsSummariesResponseDto;

import java.util.UUID;

@UseCase
public interface ReadAdminJobPostingsSummariesUseCase {

    /**
     * 4.14 (어드민) 공고 등록 수 조회하기
     */
    ReadAdminJobPostingsSummariesResponseDto execute(
            UUID accountId,
            String stringStartDate,
            String stringEndDate
    );
}
