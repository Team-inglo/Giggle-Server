package com.inglo.giggle.banner.repository;

import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

public interface BannerRepository {

    Banner findByIdOrElseNull(Long bannerId);

    Banner findByIdOrElseThrow(Long bannerId);

    List<Banner> findByRole(@Param("role") ESecurityRole role);

    List<Banner> findAll();

    void save(Banner banner);

    Banner saveAndReturn(Banner banner);

    void delete(Banner banner);

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
