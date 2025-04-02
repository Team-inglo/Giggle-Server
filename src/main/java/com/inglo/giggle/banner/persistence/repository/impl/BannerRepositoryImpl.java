package com.inglo.giggle.banner.persistence.repository.impl;

import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.banner.persistence.entity.BannerEntity;
import com.inglo.giggle.banner.persistence.entity.QBannerEntity;
import com.inglo.giggle.banner.persistence.mapper.BannerMapper;
import com.inglo.giggle.banner.persistence.repository.BannerRepository;
import com.inglo.giggle.banner.persistence.repository.mysql.BannerJpaRepository;
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
    public Banner findByIdOrElseThrow(Long bannerId) {
        return BannerMapper.toDomain(bannerJpaRepository.findById(bannerId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_BANNER)));
    }

    @Override
    public List<Banner> findByRole(ESecurityRole role) {
        return BannerMapper.toDomains(bannerJpaRepository.findByRole(role));
    }

    @Override
    public List<Banner> findAll() {
        return BannerMapper.toDomains(bannerJpaRepository.findAll());
    }

    @Override
    public Banner save(Banner banner) {
        BannerEntity entity = bannerJpaRepository.save(BannerMapper.toEntity(banner));
        return BannerMapper.toDomain(entity);
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

        QBannerEntity bannerEntity = QBannerEntity.bannerEntity;

        JPAQuery<BannerEntity> baseQuery = queryFactory
                .selectFrom(bannerEntity);

        // 검색 조건
        if (search != null && !search.isBlank()) {
            // 제목
            baseQuery.where(
                    bannerEntity.title.containsIgnoreCase(search)
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
            baseQuery.where(bannerEntity.createdAt.between(startDateTime, endDateTime));
        }

        // 필터 조건
        if (filterType != null && !filterType.isBlank()
                && filter != null && !filter.isBlank()) {
            ESecurityRole role = ESecurityRole.fromString(filter);
            baseQuery.where(bannerEntity.role.eq(role));
        }

        // 정렬 조건
        if (sortType != null && !sortType.isBlank()) {
            Order direction = (sort != null && sort.equals(Sort.Direction.DESC))
                    ? Order.DESC : Order.ASC;
            baseQuery.orderBy(new OrderSpecifier<>(direction, bannerEntity.createdAt));
        }

        long total = Optional.ofNullable(
                baseQuery.clone()
                        .select(bannerEntity.id.count())
                        .fetchOne()
        ).orElse(0L);

        List<Banner> content = BannerMapper.toDomains(baseQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch());

        return new PageImpl<>(content, pageable, total);
    }
}
