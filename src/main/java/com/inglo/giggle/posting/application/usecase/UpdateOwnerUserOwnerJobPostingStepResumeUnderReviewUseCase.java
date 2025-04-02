package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.presentation.dto.request.UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewUseCase {

    /**
     * 이력서 리뷰 상태를 업데이트합니다.
     *
     * @param accountId 계정 ID
     * @param userOwnerJobPostingId 등록한 공고 ID
     */
    void execute(
            UUID accountId,
            Long userOwnerJobPostingId,
            UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewRequestDto requestDto
    );
}
