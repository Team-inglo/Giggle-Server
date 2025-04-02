package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.presentation.dto.response.ReadAdminUserOwnerJobPostingsSummariesResponseDto;

@UseCase
public interface ReadAdminUserOwnerJobPostingsSummariesUseCase {

    /**
     * 6.16 (어드민) 공고 신청 수 조회하기
     */
    ReadAdminUserOwnerJobPostingsSummariesResponseDto execute(
            String stringStartDate,
            String stringEndDate
    );
}
