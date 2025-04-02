package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.presentation.dto.response.ReadJobPostingDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadJobPostingDetailUseCase {

    /**
     * 유학생과 고용주가 공고 게시물 상세를 조회합니다.
     *
     * @param accountId 계정 ID
     * @param jobPostingId 직무 게시물 ID
     *
     * @return 공고 게시물 상세
     */
    ReadJobPostingDetailResponseDto execute(
            UUID accountId,
            Long jobPostingId
    );

}
