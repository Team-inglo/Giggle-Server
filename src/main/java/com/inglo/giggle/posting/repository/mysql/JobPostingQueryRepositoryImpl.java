package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.QJobPosting;
import com.inglo.giggle.posting.domain.QPostingWorkDayTime;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JobPostingQueryRepositoryImpl implements JobPostingQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<JobPosting> searchWithConditions(
            String search,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String filterType,
            String filter,
            Pageable pageable
    ) {
        QJobPosting jobPosting = QJobPosting.jobPosting;

        BooleanBuilder builder = new BooleanBuilder();

        // 기간 필터
        filterByDate(startDate, endDate, builder, jobPosting);

        // 검색어 (title, addressDetail)
        filterByKeyword(search, builder, jobPosting);

        // ENUM 필터 처리
        fileterByColumn(filterType, filter, builder, jobPosting);

        // 정렬
        List<OrderSpecifier<?>> orders = getOrderSpecifiers(pageable);

        List<JobPosting> content = queryFactory
                .selectFrom(jobPosting)
                .distinct()
                .leftJoin(jobPosting.workDayTimes, QPostingWorkDayTime.postingWorkDayTime).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .fetch();

        Long totalResult = queryFactory
                .select(jobPosting.count())
                .from(jobPosting)
                .where(builder)
                .fetchOne();

        long total = totalResult != null ? totalResult : 0L;

        return new PageImpl<>(content, pageable, total);
    }

    /* -------------------------------------------- */
    /* Private Method ----------------------------- */
    /* -------------------------------------------- */
    private void filterByKeyword(String search, BooleanBuilder builder, QJobPosting jobPosting) {
        if (search != null && !search.isBlank()) {
            builder.and(
                    jobPosting.title.containsIgnoreCase(search)
                            .or(jobPosting.address.addressDetail.containsIgnoreCase(search))
                            .or(jobPosting.address.region1DepthName.containsIgnoreCase(search))
                            .or(jobPosting.address.region2DepthName.containsIgnoreCase(search))
                            .or(jobPosting.address.region3DepthName.containsIgnoreCase(search))
            );
        }
    }

    private void filterByDate(LocalDateTime startDate, LocalDateTime endDate, BooleanBuilder builder, QJobPosting jobPosting) {
        if (startDate != null && endDate != null) {
            builder.and(jobPosting.createdAt.between(startDate, endDate));
        } else if (startDate != null) {
            builder.and(jobPosting.createdAt.goe(startDate));
        } else if (endDate != null) {
            builder.and(jobPosting.createdAt.loe(endDate));
        }
    }


    private void fileterByColumn(String filterType, String filter, BooleanBuilder builder, QJobPosting jobPosting) {
        if (filterType != null && filter != null) {
            switch (filterType) {
                case "job_category" -> builder.and(jobPosting.jobCategory.stringValue().eq(filter));
                case "employment_type" -> builder.and(jobPosting.employmentType.stringValue().eq(filter));
                case "gender" -> builder.and(jobPosting.gender.stringValue().eq(filter));
                case "education_level" -> builder.and(jobPosting.educationLevel.stringValue().eq(filter));
                case "visa" -> builder.and(jobPosting.visa.any().stringValue().eq(filter));
            }
        }
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable) {
        QJobPosting jobPosting = QJobPosting.jobPosting;
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        for (Sort.Order order : pageable.getSort()) {
            PathBuilder<JobPosting> path = new PathBuilder<>(JobPosting.class, "jobPosting");

            Order orderDirection = order.isAscending() ? Order.ASC : Order.DESC;

            OrderSpecifier<?> orderSpecifier = switch (order.getProperty()) {
                case "hourlyRate", "ageRestriction" -> new OrderSpecifier<>(
                        orderDirection, path.getNumber(order.getProperty(), Integer.class)
                ).nullsLast();
                case "recruitmentDeadLine" -> new OrderSpecifier<>(
                        orderDirection, path.getDate(order.getProperty(), LocalDate.class)
                ).nullsLast();
                case "createdAt" -> new OrderSpecifier<>(
                        orderDirection, path.getDateTime(order.getProperty(), LocalDateTime.class)
                );
                default -> new OrderSpecifier<>(
                        orderDirection, path.getString(order.getProperty())
                );
            };

            orders.add(orderSpecifier);
        }

        if (orders.isEmpty()) {
            orders.add(jobPosting.createdAt.desc());
        }

        return orders;
    }
}

