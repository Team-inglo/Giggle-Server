package com.inglo.giggle.banner.application.usecase;

import com.inglo.giggle.banner.presentation.dto.response.ReadAdminBannerOverviewResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@UseCase
public interface ReadAdminBannerOverviewUseCase {

    /**
     * 12.5 (어드민) 배너 목록 조회하기
     */
    ReadAdminBannerOverviewResponseDto execute(
            Integer page,
            Integer size,
            String search,
            LocalDate startDate,
            LocalDate endDate,
            String filterType,
            String filter,
            String sortType,
            Sort.Direction sort
    );
}
