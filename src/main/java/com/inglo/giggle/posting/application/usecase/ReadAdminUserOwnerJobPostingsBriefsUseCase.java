package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.presentation.dto.response.ReadAdminUserOwnerJobPostingsBriefsResponseDto;

@UseCase
public interface ReadAdminUserOwnerJobPostingsBriefsUseCase {

    /**
     * 6.20 (어드민) 누적 신청 조회하기
     */
    ReadAdminUserOwnerJobPostingsBriefsResponseDto execute();
}
