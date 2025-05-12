package com.inglo.giggle.banner.adapter.out.persistence;

import com.inglo.giggle.banner.adapter.out.persistence.entity.BannerEntity;
import com.inglo.giggle.banner.adapter.out.persistence.mapper.BannerMapper;
import com.inglo.giggle.banner.adapter.out.persistence.repository.mysql.BannerJpaRepository;
import com.inglo.giggle.banner.application.port.out.CreateBannerPort;
import com.inglo.giggle.banner.application.port.out.DeleteBannerPort;
import com.inglo.giggle.banner.application.port.out.LoadBannerPort;
import com.inglo.giggle.banner.application.port.out.UpdateBannerPort;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.banner.persistence.entity.QBannerEntity;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
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
public class BannerPersistenceAdapter implements LoadBannerPort, CreateBannerPort, UpdateBannerPort, DeleteBannerPort {

    private final BannerJpaRepository bannerJpaRepository;
    private final BannerMapper bannerMapper;
    private final JPAQueryFactory queryFactory;

    @Override
    public Banner loadBannerOrElseThrow(Long bannerId) {
        return bannerMapper.toDomain(bannerJpaRepository.findById(bannerId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_BANNER)));
    }

    @Override
    public List<Banner> loadBanners(ESecurityRole role) {
        return bannerJpaRepository.findByRole(role)
                .stream()
                .map(bannerMapper::toDomain)
                .toList();
    }

    @Override
    public List<Banner> loadBanners() {
        return bannerJpaRepository.findAll()
                .stream()
                .map(bannerMapper::toDomain)
                .toList();
    }

    @Override
    public Page<Banner> loadBanners(
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

        List<Banner> content = baseQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(bannerMapper::toDomain)
                .toList();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public void createBanner(Banner banner) {
        bannerJpaRepository.save(bannerMapper.toEntity(banner));
    }

    @Override
    public void updateBanner(Banner banner) {
        bannerJpaRepository.save(bannerMapper.toEntity(banner));
    }

    @Override
    public void deleteBanner(Banner banner) {
        bannerJpaRepository.delete(bannerMapper.toEntity(banner));
    }
}
