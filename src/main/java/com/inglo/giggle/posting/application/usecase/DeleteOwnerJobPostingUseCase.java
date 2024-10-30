package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface DeleteOwnerJobPostingUseCase {

    /**
     * 채용공고 소유자가 채용공고를 삭제한다.
     *
     * @param accountId 사용자 ID
     * @param jobPostingId 채용공고 ID
     */
    void execute(
            UUID accountId,
            Long jobPostingId
    );
}
