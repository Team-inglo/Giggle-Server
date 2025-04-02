package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.presentation.dto.response.ReadGuestJobPostingDetailResponseDto;


@UseCase
public interface ReadGuestJobPostingDetailUseCase {

    /**
     * 게스트가 공고 게시물 상세를 조회합니다.
     *
     * @param jobPostingId 직무 게시물 ID
     *
     * @return 직무 게시물 상세
     */
    ReadGuestJobPostingDetailResponseDto execute(
            Long jobPostingId
    );
}
