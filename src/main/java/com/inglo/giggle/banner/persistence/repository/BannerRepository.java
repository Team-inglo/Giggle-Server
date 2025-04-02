package com.inglo.giggle.banner.persistence.repository;

import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

public interface BannerRepository {

    Banner findByIdOrElseThrow(Long bannerId);

    List<Banner> findByRole(ESecurityRole role);

    List<Banner> findAll();

    Banner save(Banner banner);

    void deleteById(Long bannerId);

    Page<Banner> findBannersByFilters(
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
