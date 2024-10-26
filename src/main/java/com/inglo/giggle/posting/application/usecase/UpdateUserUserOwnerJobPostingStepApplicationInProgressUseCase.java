package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface UpdateUserUserOwnerJobPostingStepApplicationInProgressUseCase {

    /**
     * 6.14 (유학생) 하이코리아 지원
     *
     * @param accountId 계정 ID
     * @param userOwnerJobPostingId 사용자-고용주 채용 공고 ID
     */
    void execute(
            UUID accountId,
            Long userOwnerJobPostingId
    );
}
