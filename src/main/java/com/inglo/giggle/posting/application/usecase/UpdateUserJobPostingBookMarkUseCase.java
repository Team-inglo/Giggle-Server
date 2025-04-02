package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.presentation.dto.response.UpdateUserJobPostingBookMarkResponseDto;

import java.util.UUID;

@UseCase
public interface UpdateUserJobPostingBookMarkUseCase {

    /**
     * 사용자의 채용공고 북마크를 업데이트한다.
     * @param userId 사용자 ID
     * @param jobPostingId 채용공고 ID
     *
     * return 북마크 업데이트 결과
     */
    UpdateUserJobPostingBookMarkResponseDto execute(
            UUID userId,
            Long jobPostingId
    );
}
