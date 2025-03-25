package com.inglo.giggle.banner.repository.impl;

import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.banner.domain.QBanner;
import com.inglo.giggle.banner.repository.BannerRepository;
import com.inglo.giggle.banner.repository.mysql.BannerJpaRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BannerRepositoryImpl implements BannerRepository {

    private final BannerJpaRepository bannerJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Banner findByIdOrElseNull(Long bannerId) {
        return bannerJpaRepository.findById(bannerId).orElse(null);
    }

    @Override
    public Banner findByIdOrElseThrow(Long bannerId) {
        return bannerJpaRepository.findById(bannerId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_BANNER));
    }

    @Override
    public List<Banner> findByRole(ESecurityRole role) {
        return bannerJpaRepository.findByRole(role);
    }

    @Override
    public List<Banner> findAll() {
        return bannerJpaRepository.findAll();
    }

    @Override
    public void save(Banner banner) {
        bannerJpaRepository.save(banner);
    }

    @Override
    public Banner saveAndReturn(Banner banner) {
        return bannerJpaRepository.save(banner);
    }

    @Override
    public void delete(Banner banner) {
        bannerJpaRepository.delete(banner);
    }

    @Override
    public void deleteById(Long bannerId) {
        bannerJpaRepository.deleteById(bannerId);
    }

    @Override
    public Page<Banner> findBannersByFilters(
            Pageable pageable,
            String search,
            LocalDate startDate,
            LocalDate endDate,
            String filterType,
            String filter,
            String sortType,
            Sort.Direction sort
    ) {

        QBanner banner = QBanner.banner;

        JPAQuery<Banner> baseQuery = queryFactory
                .selectFrom(banner);

        // 검색 조건
        if (search != null && !search.isBlank()) {
            // 제목
            baseQuery.where(
                    banner.title.containsIgnoreCase(search)
            );
        }

        // 날짜 조건
        if (startDate != null || endDate != null) {
            if (startDate == null) {
                startDate = LocalDate.of(1970, 1, 1);
            }
            if (endDate == null) {
                endDate = LocalDate.now();
            }
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
            baseQuery.where(banner.createdAt.between(startDateTime, endDateTime));
        }

        // 필터 조건
        if (filterType != null && !filterType.isBlank()
                && filter != null && !filter.isBlank()) {
            ESecurityRole role = ESecurityRole.fromString(filter);
            baseQuery.where(banner.role.eq(role));
        }

        // 정렬 조건
        if (sortType != null && !sortType.isBlank()) {
            Order direction = (sort != null && sort.equals(Sort.Direction.DESC))
                    ? Order.DESC : Order.ASC;
            baseQuery.orderBy(new OrderSpecifier<>(direction, banner.createdAt));
        }

        long total = Optional.ofNullable(
                baseQuery.clone()
                        .select(banner.id.count())
                        .fetchOne()
        ).orElse(0L);

        List<Banner> content = baseQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, total);
    }
}
