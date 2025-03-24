package com.inglo.giggle.banner.repository.mysql.querydsl;

import com.inglo.giggle.banner.domain.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import java.time.LocalDate;

public interface BannerRepositoryQuery {

    Page<Banner> findBannersByFilters(
            Pageable pageable,
            String search,
            LocalDate startDate,
            LocalDate endDate,
            String filterType,
            String filter,
            String sortType,
            Direction sort
    );
}
