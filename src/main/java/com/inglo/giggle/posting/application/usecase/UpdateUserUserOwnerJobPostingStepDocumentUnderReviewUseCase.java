package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface UpdateUserUserOwnerJobPostingStepDocumentUnderReviewUseCase {

        /**
        * 6.13 (유학생) 유학생 담당자 검토 완료
        *
        * @param accountId 계정 ID
        * @param userOwnerJobPostingId 등록된 공고 ID
        */
        void execute(
                UUID accountId,
                Long userOwnerJobPostingId
        );
}
