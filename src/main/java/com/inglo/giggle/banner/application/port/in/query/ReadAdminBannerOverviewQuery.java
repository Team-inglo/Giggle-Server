package com.inglo.giggle.banner.application.port.in.query;

import com.inglo.giggle.banner.application.port.in.result.ReadAdminBannerOverviewResult;
import com.inglo.giggle.core.annotation.bean.UseCase;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@UseCase
public interface ReadAdminBannerOverviewQuery {

    /**
     * 12.5 (어드민) 배너 목록 조회하기
     */
    ReadAdminBannerOverviewResult execute(
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
