package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.presentation.dto.response.ReadOwnerUserOwnerJobPostingDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadOwnerUserOwnerJobPostingDetailUseCase {

    /**
     * 6.7 (고용주) 지원자 지원 상태 상세 조회
     *
     * @param accountId 계정 ID
     * @param userOwnerJobPostingsId 사용자-고용주 공고 ID
     *
     * @return 지원자 지원 상태 상세
     */
    ReadOwnerUserOwnerJobPostingDetailResponseDto execute(
            UUID accountId,
            Long userOwnerJobPostingsId
    );
}
