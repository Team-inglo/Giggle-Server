package com.inglo.giggle.posting.repository.impl;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.QUserOwnerJobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import com.inglo.giggle.posting.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingJpaRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserOwnerJobPostingRepositoryImpl implements UserOwnerJobPostingRepository {

    private final UserOwnerJobPostingJpaRepository userOwnerJobPostingJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public UserOwnerJobPosting findByIdOrElseThrow(Long id) {
        return userOwnerJobPostingJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER_OWNER_JOB_POSTING));
    }

    @Override
    public Page<UserOwnerJobPosting> findAllPagedWithJobPostingByUser(User user, Pageable pageable) {
        return userOwnerJobPostingJpaRepository.findAllPagedWithJobPostingByUser(user, pageable);
    }

    @Override
    public Page<UserOwnerJobPosting> findAllPagedWithJobPostingAndOwnerByUser(User user, Pageable pageable) {
        return userOwnerJobPostingJpaRepository.findAllPagedWithJobPostingAndOwnerByUser(user, pageable);
    }

    @Override
    public Page<UserOwnerJobPosting> findAllPagedWithJobPostingByUserAndStep(User user, EApplicationStep step, Pageable pageable) {
        return userOwnerJobPostingJpaRepository.findAllPagedWithJobPostingByUserAndStep(user, step, pageable);
    }

    @Override
    public Page<UserOwnerJobPosting> findAllPagedWithJobPostingByUserAndSteps(
            User user,
            List<EApplicationStep> steps,
            Pageable pageable
    ) {
        return userOwnerJobPostingJpaRepository.findAllPagedWithJobPostingByUserAndSteps(user, steps, pageable);
    }

    @Override
    public Optional<UserOwnerJobPosting> findWithJobPostingAndOwnerAndJobPostingsWorkDayTimesById(Long id) {
        return userOwnerJobPostingJpaRepository.findWithJobPostingAndOwnerAndJobPostingsWorkDayTimesById(id);
    }

    @Override
    public List<UserOwnerJobPosting> findAllWithJobPostingByUser(User user) {
        return userOwnerJobPostingJpaRepository.findAllWithJobPostingByUser(user);
    }

    @Override
    public Optional<UserOwnerJobPosting> findWithJobPostingById(Long id) {
        return userOwnerJobPostingJpaRepository.findWithJobPostingById(id);
    }

    @Override
    public Optional<UserOwnerJobPosting> findWithJobPostingAndOwnerById(Long id) {
        return userOwnerJobPostingJpaRepository.findWithJobPostingAndOwnerById(id);
    }

    @Override
    public Optional<UserOwnerJobPosting> findWithJobPostingAndUserById(Long id) {
        return userOwnerJobPostingJpaRepository.findWithJobPostingAndUserById(id);
    }

    @Override
    public Optional<UserOwnerJobPosting> findWithOwnerAndUserJobPostingById(Long id) {
        return userOwnerJobPostingJpaRepository.findWithOwnerAndUserJobPostingById(id);
    }

    @Override
    public Optional<UserOwnerJobPosting> findWithUserById(Long userOwnerJobPostingsId) {
        return userOwnerJobPostingJpaRepository.findWithUserById(userOwnerJobPostingsId);
    }

    @Override
    public List<UserOwnerJobPosting> findAllWithJobPostingByOwner(Owner owner) {
        return userOwnerJobPostingJpaRepository.findAllWithJobPostingByOwner(owner);
    }

    @Override
    public Page<UserOwnerJobPosting> findAllPageWithUserByJobPosting(JobPosting jobPosting, Pageable pageable) {
        return userOwnerJobPostingJpaRepository.findAllPageWithUserByJobPosting(jobPosting, pageable);
    }

    @Override
    public Boolean existsByUserAndJobPosting(User user, JobPosting jobPosting) {
        return userOwnerJobPostingJpaRepository.existsByUserAndJobPosting(user, jobPosting);
    }

    @Override
    public Page<UserOwnerJobPosting> findAllPageWithUserByJobPostingAndStep(JobPosting jobPosting, EApplicationStep step, Pageable pageable) {
        return userOwnerJobPostingJpaRepository.findAllPageWithUserByJobPostingAndStep(jobPosting, step, pageable);
    }

    @Override
    public Page<UserOwnerJobPosting> findAllPageWithUserByJobPostingAndSteps(
            JobPosting jobPosting,
            List<EApplicationStep> steps,
            Pageable pageable
    ) {
        return userOwnerJobPostingJpaRepository.findAllPageWithUserByJobPostingAndSteps(jobPosting, steps, pageable);
    }

    @Override
    public int countByCreatedAtBetween(LocalDateTime start, LocalDateTime end) {
        return userOwnerJobPostingJpaRepository.countByCreatedAtBetween(start, end);
    }

    @Override
    public int countUserOwnerJobPostingByStepAndCreatedAtBetween(
            EApplicationStep step,
            LocalDateTime start,
            LocalDateTime end
    ) {
        return userOwnerJobPostingJpaRepository.countUserOwnerJobPostingByStepAndCreatedAtBetween(step, start, end);
    }

    @Override
    public int countByStep(EApplicationStep step) {
        return userOwnerJobPostingJpaRepository.countByStep(step);
    }

    @Override
    public void save(UserOwnerJobPosting userOwnerJobPosting) {
        userOwnerJobPostingJpaRepository.save(userOwnerJobPosting);
    }

    @Override
    public UserOwnerJobPosting saveAndReturn(UserOwnerJobPosting userOwnerJobPosting) {
        return userOwnerJobPostingJpaRepository.save(userOwnerJobPosting);
    }

    @Override
    public void delete(UserOwnerJobPosting userOwnerJobPosting) {
        userOwnerJobPostingJpaRepository.delete(userOwnerJobPosting);
    }

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

    @Override
    public List<GraphStats> countGraphStatsByMonth(LocalDateTime startDate, LocalDateTime endDate) {
        QUserOwnerJobPosting q = QUserOwnerJobPosting.userOwnerJobPosting;

        return queryFactory
                .select(com.querydsl.core.types.Projections.constructor(
                        GraphStats.class,
                        q.createdAt.year(),
                        q.createdAt.month(),
                        q.id.count(),
                        Expressions.numberTemplate(Long.class,
                                "SUM(CASE WHEN {0} != {1} THEN 1 ELSE 0 END)",
                                q.step, EApplicationStep.RESUME_REJECTED
                        ),
                        Expressions.numberTemplate(Long.class,
                                "SUM(CASE WHEN {0} = {1} THEN 1 ELSE 0 END)",
                                q.step, EApplicationStep.RESUME_REJECTED
                        ),
                        Expressions.numberTemplate(Long.class,
                                "SUM(CASE WHEN {0} = {1} THEN 1 ELSE 0 END)",
                                q.step, EApplicationStep.APPLICATION_SUCCESS
                        )
                ))
                .from(q)
                .where(q.createdAt.between(startDate, endDate.minusNanos(1)))
                .groupBy(q.createdAt.year(), q.createdAt.month())
                .orderBy(q.createdAt.year().asc(), q.createdAt.month().asc())
                .fetch();
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

    @Override
    public long count() {
        return userOwnerJobPostingJpaRepository.count();
    }
}
