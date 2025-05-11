package com.inglo.giggle.posting.persistence.repository.impl;


import com.inglo.giggle.owner.domain.Owner;
import com.inglo.giggle.user.domain.User;
import com.inglo.giggle.owner.adapter.out.persistence.mapper.OwnerMapper;
import com.inglo.giggle.user.adapter.out.persistence.mapper.UserMapper;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.persistence.mapper.DocumentMapper;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import com.inglo.giggle.posting.persistence.entity.QUserOwnerJobPostingEntity;
import com.inglo.giggle.posting.persistence.entity.UserOwnerJobPostingEntity;
import com.inglo.giggle.posting.persistence.mapper.JobPostingMapper;
import com.inglo.giggle.posting.persistence.mapper.UserOwnerJobPostingMapper;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.posting.persistence.repository.mysql.UserOwnerJobPostingJpaRepository;
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

@Repository
@RequiredArgsConstructor
public class UserOwnerJobPostingRepositoryImpl implements UserOwnerJobPostingRepository {

    private final UserOwnerJobPostingJpaRepository userOwnerJobPostingJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public UserOwnerJobPosting findByIdOrElseThrow(Long id) {
        return UserOwnerJobPostingMapper.toDomain(userOwnerJobPostingJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER_OWNER_JOB_POSTING)));
    }

    @Override
    public UserOwnerJobPosting findByDocumentOrElseThrow(Document document) {
        return UserOwnerJobPostingMapper.toDomain(userOwnerJobPostingJpaRepository.findByDocument(DocumentMapper.toEntity(document))
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER_OWNER_JOB_POSTING)));
    }

    @Override
    public Page<UserOwnerJobPosting> findAllPagedWithJobPostingByUser(User user, Pageable pageable) {
        return userOwnerJobPostingJpaRepository.findAllPagedWithJobPostingByUserEntity(UserMapper.toEntity(user), pageable)
                .map(UserOwnerJobPostingMapper::toDomain);
    }

    @Override
    public Page<UserOwnerJobPosting> findAllPagedWithJobPostingAndOwnerByUser(User user, Pageable pageable) {
        return userOwnerJobPostingJpaRepository.findAllPagedWithJobPostingAndOwnerByUserEntity(UserMapper.toEntity(user), pageable)
                .map(UserOwnerJobPostingMapper::toDomain);
    }

    @Override
    public Page<UserOwnerJobPosting> findAllPagedWithJobPostingByUserAndStep(User user, EApplicationStep step, Pageable pageable) {
        return userOwnerJobPostingJpaRepository.findAllPagedWithJobPostingByUserEntityAndStep(UserMapper.toEntity(user), step, pageable)
                .map(UserOwnerJobPostingMapper::toDomain);
    }

    @Override
    public Page<UserOwnerJobPosting> findAllPagedWithJobPostingByUserAndSteps(
            User user,
            List<EApplicationStep> steps,
            Pageable pageable
    ) {
        return userOwnerJobPostingJpaRepository.findAllPagedWithJobPostingByUserAndSteps(UserMapper.toEntity(user), steps, pageable)
                .map(UserOwnerJobPostingMapper::toDomain);
    }

    @Override
    public UserOwnerJobPosting findWithJobPostingAndOwnerAndJobPostingsWorkDayTimesByIdOrElseThrow(Long id) {
        return userOwnerJobPostingJpaRepository.findWithJobPostingAndOwnerAndJobPostingsWorkDayTimesById(id)
                .map(UserOwnerJobPostingMapper::toDomain)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER_OWNER_JOB_POSTING));
    }

    @Override
    public List<UserOwnerJobPosting> findAllWithJobPostingByUser(User user) {
        return UserOwnerJobPostingMapper.toDomains(userOwnerJobPostingJpaRepository.findAllWithJobPostingByUserEntity(UserMapper.toEntity(user)));
    }

    @Override
    public UserOwnerJobPosting findWithJobPostingByIdOrElseThrow(Long id) {
        return UserOwnerJobPostingMapper.toDomain(userOwnerJobPostingJpaRepository.findWithJobPostingById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER_OWNER_JOB_POSTING)));
    }

    @Override
    public UserOwnerJobPosting findWithJobPostingAndOwnerByIdOrElseThrow(Long id) {
        return UserOwnerJobPostingMapper.toDomain(userOwnerJobPostingJpaRepository.findWithJobPostingAndOwnerById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER_OWNER_JOB_POSTING)));
    }

    @Override
    public UserOwnerJobPosting findWithJobPostingAndUserByIdOrElseThrow(Long id) {
        return UserOwnerJobPostingMapper.toDomain(userOwnerJobPostingJpaRepository.findWithJobPostingAndUserById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER_OWNER_JOB_POSTING)));
    }

    @Override
    public UserOwnerJobPosting findWithOwnerAndUserJobPostingByIdOrElseThrow(Long id) {
        return UserOwnerJobPostingMapper.toDomain(userOwnerJobPostingJpaRepository.findWithOwnerAndUserJobPostingById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER_OWNER_JOB_POSTING)));
    }

    @Override
    public UserOwnerJobPosting findWithUserByIdOrElseThrow(Long userOwnerJobPostingsId) {
        return UserOwnerJobPostingMapper.toDomain(userOwnerJobPostingJpaRepository.findWithUserById(userOwnerJobPostingsId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER_OWNER_JOB_POSTING)));
    }

    @Override
    public List<UserOwnerJobPosting> findAllWithJobPostingByOwner(Owner owner) {
        return UserOwnerJobPostingMapper.toDomains(userOwnerJobPostingJpaRepository.findAllWithJobPostingByOwnerEntity(OwnerMapper.toEntity(owner)));
    }

    @Override
    public Page<UserOwnerJobPosting> findAllPageWithUserByJobPosting(JobPosting jobPosting, Pageable pageable) {
        return userOwnerJobPostingJpaRepository.findAllPageWithUserByJobPostingEntity(JobPostingMapper.toEntity(jobPosting), pageable)
                .map(UserOwnerJobPostingMapper::toDomain);
    }

    @Override
    public Boolean existsByUserAndJobPosting(User user, JobPosting jobPosting) {
        return userOwnerJobPostingJpaRepository.existsByUserEntityAndJobPostingEntity(UserMapper.toEntity(user), JobPostingMapper.toEntity(jobPosting));
    }

    @Override
    public Page<UserOwnerJobPosting> findAllPageWithUserByJobPostingAndStep(JobPosting jobPosting, EApplicationStep step, Pageable pageable) {
        return userOwnerJobPostingJpaRepository.findAllPageWithUserByJobPostingEntityAndStep(JobPostingMapper.toEntity(jobPosting), step, pageable)
                .map(UserOwnerJobPostingMapper::toDomain);
    }

    @Override
    public Page<UserOwnerJobPosting> findAllPageWithUserByJobPostingAndSteps(
            JobPosting jobPosting,
            List<EApplicationStep> steps,
            Pageable pageable
    ) {
        return userOwnerJobPostingJpaRepository.findAllPageWithUserByJobPostingAndSteps(JobPostingMapper.toEntity(jobPosting), steps, pageable)
                .map(UserOwnerJobPostingMapper::toDomain);
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
    public UserOwnerJobPosting save(UserOwnerJobPosting userOwnerJobPosting) {
        UserOwnerJobPostingEntity entity = userOwnerJobPostingJpaRepository.save(UserOwnerJobPostingMapper.toEntity(userOwnerJobPosting));
        return UserOwnerJobPostingMapper.toDomain(entity);
    }

    @Override
    public void delete(UserOwnerJobPosting userOwnerJobPosting) {
        userOwnerJobPostingJpaRepository.delete(UserOwnerJobPostingMapper.toEntity(userOwnerJobPosting));
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
        QUserOwnerJobPostingEntity userOwnerJobPostingEntity = QUserOwnerJobPostingEntity.userOwnerJobPostingEntity;

        BooleanBuilder builder = new BooleanBuilder();

        // 기간 필터
        filterByDate(startDate, endDate, builder, userOwnerJobPostingEntity);

        // 검색어 (title, name, memo)
        filterByKeyword(search, builder, userOwnerJobPostingEntity);

        // ENUM 필터 처리
        fileterByColumn(filterType, filter, builder, userOwnerJobPostingEntity);

        // 정렬
        List<OrderSpecifier<?>> orders = getOrderSpecifiers(pageable);

        List<UserOwnerJobPosting> content = UserOwnerJobPostingMapper.toDomains(queryFactory
                .selectFrom(userOwnerJobPostingEntity)
                .distinct()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .fetch());

        Long totalResult = queryFactory
                .select(userOwnerJobPostingEntity.count())
                .from(userOwnerJobPostingEntity)
                .where(builder)
                .fetchOne();

        long total = totalResult != null ? totalResult : 0L;

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<GraphStats> countGraphStatsByMonth(LocalDateTime startDate, LocalDateTime endDate) {
        QUserOwnerJobPostingEntity q = QUserOwnerJobPostingEntity.userOwnerJobPostingEntity;

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
    private void filterByDate(LocalDateTime startDate, LocalDateTime endDate, BooleanBuilder builder, QUserOwnerJobPostingEntity userOwnerJobPostingEntity) {
        if (startDate != null && endDate != null) {
            builder.and(userOwnerJobPostingEntity.createdAt.between(startDate, endDate));
        } else if (startDate != null) {
            builder.and(userOwnerJobPostingEntity.createdAt.goe(startDate));
        } else if (endDate != null) {
            builder.and(userOwnerJobPostingEntity.createdAt.loe(endDate));
        }
    }

    private void filterByKeyword(String search, BooleanBuilder builder, QUserOwnerJobPostingEntity userOwnerJobPostingEntity) {
        if (search != null) {
            builder.and(
                    userOwnerJobPostingEntity.jobPostingEntity.title.contains(search)
                            .or(userOwnerJobPostingEntity.userEntity.serialId.contains(search))
                            .or(userOwnerJobPostingEntity.userEntity.firstName.contains(search))
                            .or(userOwnerJobPostingEntity.userEntity.lastName.contains(search))
                            .or(userOwnerJobPostingEntity.memo.contains(search))
            );
        }
    }

    private void fileterByColumn(String filterType, String filter, BooleanBuilder builder, QUserOwnerJobPostingEntity userOwnerJobPosting) {
        if (filterType != null && filter != null && EApplicationStep.isExisted(filter)) {
            builder.and(userOwnerJobPosting.step.stringValue().eq(filter));
        }
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable) {
        QUserOwnerJobPostingEntity userOwnerJobPosting = QUserOwnerJobPostingEntity.userOwnerJobPostingEntity;
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
