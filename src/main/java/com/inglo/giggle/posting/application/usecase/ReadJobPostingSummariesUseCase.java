package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.application.dto.response.ReadJobPostingSummariesResponseDto;

import java.util.UUID;

@UseCase
public interface ReadJobPostingSummariesUseCase {

    /**
     * (유학생/고용주) 목록 조회
     *
     * @param accountId 계정 ID
     * @param jobPostingId 채용공고 ID
     *
     * @return 채용공고 요약 목록
     */
    ReadJobPostingSummariesResponseDto execute(
            UUID accountId,
            Long jobPostingId
    );
}
