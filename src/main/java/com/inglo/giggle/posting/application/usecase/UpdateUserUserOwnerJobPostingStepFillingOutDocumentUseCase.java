package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface UpdateUserUserOwnerJobPostingStepFillingOutDocumentUseCase {

    /**
     * 6.12 (사용자) 문서 작성 완료하기
     *
     * @param accountId 계정 ID
     * @param userOwnerJobPostingId 등록된 공고 ID
     */
    void execute(
            UUID accountId,
            Long userOwnerJobPostingId
    );
}
