package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.presentation.dto.response.ReadOwnerUserOwnerJobPostingCountResponseDto;

import java.util.UUID;

@UseCase
public interface ReadOwnerUserOwnerJobPostingCountUseCase {

    /**
     * 6.9 (고용주) 지원 현황(개수) 확인하기
     *
     * @param accountId 계정 ID
     *
     * @return 전체 지원자 현황 DTO
     */
    ReadOwnerUserOwnerJobPostingCountResponseDto execute(
            UUID accountId
    );
}
