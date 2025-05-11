package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.port.in.query.ReadAdminBannerOverviewQuery;
import com.inglo.giggle.banner.application.port.in.result.ReadAdminBannerOverviewResult;
import com.inglo.giggle.banner.application.port.out.LoadBannerPort;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.core.dto.PageInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReadAdminBannerOverviewService implements ReadAdminBannerOverviewQuery {

    private final LoadBannerPort loadBannerPort;

    @Override
    @Transactional(readOnly = true)
    public ReadAdminBannerOverviewResult execute(
            Integer page,
            Integer size,
            String search,
            LocalDate startDate,
            LocalDate endDate,
            String filterType,
            String filter,
            String sortType,
            Direction sort
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Banner> bannerPage = loadBannerPort.loadBanners(
                pageable,
                search,
                startDate,
                endDate,
                filterType,
                filter,
                sortType,
                sort
        );

        PageInfoDto pageInfo = PageInfoDto.of(
                bannerPage.getNumber() + 1,
                bannerPage.getContent().size(),
                bannerPage.getSize(),
                bannerPage.getTotalPages(),
                (int) bannerPage.getTotalElements()
        );

        return ReadAdminBannerOverviewResult.of(
                bannerPage.getContent(),
                pageInfo
        );
    }
}
