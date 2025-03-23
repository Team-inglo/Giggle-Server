package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.application.dto.response.ReadAdminJobPostingsOverviewsResponseDto;

@UseCase
public interface ReadAdminJobPostingsOverviewsUseCase {

    /**
     * 4.15 (어드민) 공고 목록 조회하기
     */
    ReadAdminJobPostingsOverviewsResponseDto execute(
            Integer page,
            Integer size,
            String search,
            String startDate,
            String endDate,
            String filterType,
            String filter,
            String sortType,
            String sort
    );
}
