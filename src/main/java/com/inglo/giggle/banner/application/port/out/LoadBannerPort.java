package com.inglo.giggle.banner.application.port.out;

import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

public interface LoadBannerPort {

    Banner loadBanner(Long bannerId);

    List<Banner> loadBanners(ESecurityRole role);

    List<Banner> loadBanners();

    Page<Banner> loadBanners(
            Pageable pageable,
            String search,
            LocalDate startDate,
            LocalDate endDate,
            String filterType,
            String filter,
            String sortType,
            Sort.Direction sort
    );
}
