package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.presentation.dto.response.ReadAdminUserOwnerJobPostingsOverviewsResponseDto;

@UseCase
public interface ReadAdminUserOwnerJobPostingsOverviewsUseCase {

    /**
     * 6.18 (어드민) 지원 목록 조회하기
     */
    ReadAdminUserOwnerJobPostingsOverviewsResponseDto execute(
            Integer page,
            Integer size,
            String search,
            String stringStartDate,
            String stringEndDate,
            String filterType,
            String filter,
            String sortType,
            String sort
    );
}
