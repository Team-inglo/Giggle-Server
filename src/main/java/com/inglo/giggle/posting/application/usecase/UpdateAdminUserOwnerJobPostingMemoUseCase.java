package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

@UseCase
public interface UpdateAdminUserOwnerJobPostingMemoUseCase {

    /**
     * 6.21 (어드민) 공고 메모 수정하기
     */
    void execute(
            Long userOwnerJobPostingId,
            String memo
    );
}
