package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.presentation.dto.response.ReadJobPostingSummariesResponseDto;

@UseCase
public interface ReadJobPostingSummariesUseCase {

    /**
     * (유학생/고용주) 목록 조회
     *
     * @param jobPostingId 채용공고 ID
     *
     * @return 채용공고 요약 목록
     */
    ReadJobPostingSummariesResponseDto execute(
            Long jobPostingId
    );
}
