package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.application.dto.request.UpdateUserUserOwnerJobPostingStepRegisteringResultRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateUserUserOwnerJobPostingStepRegisteringResultUseCase {

    /**
     * 6.10 (유학생) 하이코리아 처리결과 등록하기
     *
     * @param accountId             계정 ID
     * @param userOwnerJobPostingId 사용자-고용주 채용 공고 ID
     * @param requestDto            결과
     */
    void execute(
            UUID accountId,
            Long userOwnerJobPostingId,
            UpdateUserUserOwnerJobPostingStepRegisteringResultRequestDto requestDto
    );
}
