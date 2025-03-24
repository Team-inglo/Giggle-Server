package com.inglo.giggle.banner.repository.mysql.querydsl;

import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.banner.domain.QBanner;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BannerRepositoryQueryImpl implements BannerRepositoryQuery {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Banner> findBannersByFilters(
            Pageable pageable,
            String search,
            LocalDate startDate,
            LocalDate endDate,
            String filterType,
            String filter,
            String sortType,
            Direction sort
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
            Order direction = (sort != null && sort.equals(Direction.DESC))
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
