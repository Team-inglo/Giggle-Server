package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.posting.domain.QUserOwnerJobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserOwnerJobPostingRepositoryImpl implements UserOwnerJobPostingQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<UserOwnerJobPosting> searchWithConditions(
            String search,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String filterType,
            String filter,
            Pageable pageable
    ) {
        QUserOwnerJobPosting userOwnerJobPosting = QUserOwnerJobPosting.userOwnerJobPosting;

        BooleanBuilder builder = new BooleanBuilder();

        // 기간 필터
        filterByDate(startDate, endDate, builder, userOwnerJobPosting);

        // 검색어 (title, name, memo)
        filterByKeyword(search, builder, userOwnerJobPosting);

        // ENUM 필터 처리
        fileterByColumn(filterType, filter, builder, userOwnerJobPosting);

        // 정렬
        List<OrderSpecifier<?>> orders = getOrderSpecifiers(pageable);

        List<UserOwnerJobPosting> content = queryFactory
                .selectFrom(userOwnerJobPosting)
                .distinct()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .fetch();

        Long totalResult = queryFactory
                .select(userOwnerJobPosting.count())
                .from(userOwnerJobPosting)
                .where(builder)
                .fetchOne();

        long total = totalResult != null ? totalResult : 0L;

        return new PageImpl<>(content, pageable, total);
    }

    /* -------------------------------------------- */
    /* Private Method ----------------------------- */
    /* -------------------------------------------- */
    private void filterByDate(LocalDateTime startDate, LocalDateTime endDate, BooleanBuilder builder, QUserOwnerJobPosting userOwnerJobPosting) {
        if (startDate != null && endDate != null) {
            builder.and(userOwnerJobPosting.createdAt.between(startDate, endDate));
        } else if (startDate != null) {
            builder.and(userOwnerJobPosting.createdAt.goe(startDate));
        } else if (endDate != null) {
            builder.and(userOwnerJobPosting.createdAt.loe(endDate));
        }
    }

    private void filterByKeyword(String search, BooleanBuilder builder, QUserOwnerJobPosting userOwnerJobPosting) {
        if (search != null) {
            builder.and(
                    userOwnerJobPosting.jobPosting.title.contains(search)
                            .or(userOwnerJobPosting.user.serialId.contains(search))
                            .or(userOwnerJobPosting.user.firstName.contains(search))
                            .or(userOwnerJobPosting.user.lastName.contains(search))
                            .or(userOwnerJobPosting.memo.contains(search))
            );
        }
    }

    private void fileterByColumn(String filterType, String filter, BooleanBuilder builder, QUserOwnerJobPosting userOwnerJobPosting) {
        if (filterType != null && filter != null && EApplicationStep.isExisted(filter)) {
            builder.and(userOwnerJobPosting.step.stringValue().eq(filter));
        }
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable) {
        QUserOwnerJobPosting userOwnerJobPosting = QUserOwnerJobPosting.userOwnerJobPosting;
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        for (Sort.Order order : pageable.getSort()) {
            PathBuilder<UserOwnerJobPosting> path = new PathBuilder<>(UserOwnerJobPosting.class, "userOwnerJobPosting");

            Order orderDirection = order.isAscending() ? Order.ASC : Order.DESC;

            OrderSpecifier<?> orderSpecifier = switch (order.getProperty()) {
                case "lastStepUpdated" -> new OrderSpecifier<>(
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
            orders.add(userOwnerJobPosting.createdAt.desc());
        }

        return orders;
    }
}
