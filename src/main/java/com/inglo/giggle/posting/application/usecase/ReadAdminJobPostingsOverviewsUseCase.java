package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.application.dto.response.ReadAdminJobPostingsOverviewsResponseDto;

import java.util.UUID;

@UseCase
public interface ReadAdminJobPostingsOverviewsUseCase {

    /**
     * 4.14 (어드민) 공고 등록 수 조회하기
     */
    ReadAdminJobPostingsOverviewsResponseDto execute(
            UUID accountId,
            String stringStartDate,
            String stringEndDate
    );
}
