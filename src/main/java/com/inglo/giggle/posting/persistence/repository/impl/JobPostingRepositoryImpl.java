package com.inglo.giggle.posting.persistence.repository.impl;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.persistence.mapper.OwnerMapper;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import com.inglo.giggle.posting.domain.type.EWorkingHours;
import com.inglo.giggle.posting.persistence.entity.JobPostingEntity;
import com.inglo.giggle.posting.persistence.entity.QJobPostingEntity;
import com.inglo.giggle.posting.persistence.entity.QPostingWorkDayTimeEntity;
import com.inglo.giggle.posting.persistence.mapper.JobPostingMapper;
import com.inglo.giggle.posting.persistence.repository.JobPostingRepository;
import com.inglo.giggle.posting.persistence.repository.mysql.JobPostingJpaRepository;
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
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JobPostingRepositoryImpl implements JobPostingRepository {

    private final JobPostingJpaRepository jobPostingJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public JobPosting findByIdOrElseThrow(Long id) {
        return JobPostingMapper.toDomain(jobPostingJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_JOB_POSTING)));
    }

    @Override
    public List<JobPosting> findAll() {
        return JobPostingMapper.toDomains(jobPostingJpaRepository.findAll());
    }

    @Override
    public List<JobPosting> findAllByOwner(Owner owner) {
        return JobPostingMapper.toDomains(jobPostingJpaRepository.findAllByOwnerEntity(OwnerMapper.toEntity(owner)));
    }

    @Override
    public JobPosting findWithOwnerByIdOrElseThrow(Long jobPostingId) {
        return jobPostingJpaRepository.findWithOwnerById(jobPostingId).map(JobPostingMapper::toDomain)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_JOB_POSTING));
    }

    @Override
    public Page<JobPosting> findWithPageByOwner(Owner owner, Pageable pageRequest) {
        return jobPostingJpaRepository.findWithPageByOwnerEntity(OwnerMapper.toEntity(owner), pageRequest)
                .map(JobPostingMapper::toDomain);
    }

    // 인기순 공고 조회 쿼리
    @Override
    public Page<JobPostingProjection> findPopularJobPostingsWithFilters(
            String jobTitle,
            String region1Depth1,
            String region1Depth2,
            String region1Depth3,
            String region2Depth1,
            String region2Depth2,
            String region2Depth3,
            String region3Depth1,
            String region3Depth2,
            String region3Depth3,
            List<EJobCategory> industryList,
            List<EWorkPeriod> workPeriodList,
            List<Integer> workDaysPerWeekList,
            List<EDayOfWeek> workingDayList,
            List<EWorkingHours> workingHoursList,
            LocalTime morningStart,
            LocalTime morningEnd,
            LocalTime eveningStart,
            LocalTime afternoonStart,
            LocalTime afternoonEnd,
            LocalTime eveningEnd,
            LocalTime fullDayStart,
            LocalTime fullDayEnd,
            LocalTime dawnStart,
            LocalTime dawnEnd,
            boolean morningSelected,
            boolean afternoonSelected,
            boolean eveningSelected,
            boolean fullDaySelected,
            boolean dawnSelected,
            EDayOfWeek negotiableDay,
            LocalDate today,
            String recruitmentPeriod,
            EEmploymentType employmentType,
            Set<EVisa> visa,
            Pageable pageable
    ) {
        return jobPostingJpaRepository.findPopularJobPostingsWithFilters(
                jobTitle,
                region1Depth1,
                region1Depth2,
                region1Depth3,
                region2Depth1,
                region2Depth2,
                region2Depth3,
                region3Depth1,
                region3Depth2,
                region3Depth3,
                industryList,
                workPeriodList,
                workDaysPerWeekList,
                workingDayList,
                workingHoursList,
                morningStart,
                morningEnd,
                eveningStart,
                afternoonStart,
                afternoonEnd,
                eveningEnd,
                fullDayStart,
                fullDayEnd,
                dawnStart,
                dawnEnd,
                morningSelected,
                afternoonSelected,
                eveningSelected,
                fullDaySelected,
                dawnSelected,
                negotiableDay,
                today,
                recruitmentPeriod,
                employmentType,
                visa,
                pageable
        );
    }

    // 최신순 공고 조회 쿼리
    @Override
    public Page<JobPostingProjection> findRecentJobPostingsWithFilters(
            String jobTitle,
            String region1Depth1,
            String region1Depth2,
            String region1Depth3,
            String region2Depth1,
            String region2Depth2,
            String region2Depth3,
            String region3Depth1,
            String region3Depth2,
            String region3Depth3,
            List<EJobCategory> industryList,
            List<EWorkPeriod> workPeriodList,
            List<Integer> workDaysPerWeekList,
            List<EDayOfWeek> workingDayList,
            List<EWorkingHours> workingHoursList,
            LocalTime morningStart,
            LocalTime morningEnd,
            LocalTime afternoonStart,
            LocalTime afternoonEnd,
            LocalTime eveningStart,
            LocalTime eveningEnd,
            LocalTime fullDayStart,
            LocalTime fullDayEnd,
            LocalTime dawnStart,
            LocalTime dawnEnd,
            boolean morningSelected,
            boolean afternoonSelected,
            boolean eveningSelected,
            boolean fullDaySelected,
            boolean dawnSelected,
            EDayOfWeek negotiableDay,
            LocalDate today,
            String recruitmentPeriod,
            EEmploymentType employmentType,
            Set<EVisa> visa,
            Pageable pageable
    ) {
        return jobPostingJpaRepository.findRecentJobPostingsWithFilters(
                jobTitle,
                region1Depth1,
                region1Depth2,
                region1Depth3,
                region2Depth1,
                region2Depth2,
                region2Depth3,
                region3Depth1,
                region3Depth2,
                region3Depth3,
                industryList,
                workPeriodList,
                workDaysPerWeekList,
                workingDayList,
                workingHoursList,
                morningStart,
                morningEnd,
                eveningStart,
                afternoonStart,
                afternoonEnd,
                eveningEnd,
                fullDayStart,
                fullDayEnd,
                dawnStart,
                dawnEnd,
                morningSelected,
                afternoonSelected,
                eveningSelected,
                fullDaySelected,
                dawnSelected,
                negotiableDay,
                today,
                recruitmentPeriod,
                employmentType,
                visa,
                pageable
        );
    }

    @Override
    public List<JobPosting> findJobPostingsWithDetailsByIds(
            List<Long> jobPostingIds
    ) {
        return JobPostingMapper.toDomains(jobPostingJpaRepository.findJobPostingsWithDetailsByIds(jobPostingIds));
    }

    @Override
    public List<JobPosting> findJobPostingsWithCompanyImagesByIds(
            List<Long> jobPostingIds
    ){
        return JobPostingMapper.toDomains(jobPostingJpaRepository.findJobPostingsWithCompanyImagesByIds(jobPostingIds));
    }

    @Override
    public Page<JobPostingProjection> findTrendingJobPostingsWithFetchJoin(
            LocalDate today,
            Pageable pageable
    ) {
        return jobPostingJpaRepository.findTrendingJobPostingsWithFetchJoin(today, pageable);
    }

    @Override
    public Page<JobPostingProjection> findRecentlyJobPostingsWithFetchJoin(
            LocalDate today,
            Pageable pageable
    ) {
        return jobPostingJpaRepository.findRecentlyJobPostingsWithFetchJoin(today, pageable);
    }

    @Override
    public Page<JobPostingProjection> findBookmarkedJobPostingsWithFetchJoin(
            UUID accountId,
            LocalDate today,
            Pageable pageable
    ) {
        return jobPostingJpaRepository.findBookmarkedJobPostingsWithFetchJoin(accountId, today, pageable);
    }

    @Override
    public List<Object[]> countBookmarksByJobPostingIdsAndAccountId(List<Long> jobPostingIds, UUID accountId) {
        return jobPostingJpaRepository.countBookmarksByJobPostingIdsAccountId(jobPostingIds, accountId);
    }

    @Override
    public int countByCreatedAtBetween(LocalDateTime start, LocalDateTime end) {
        return jobPostingJpaRepository.countByCreatedAtBetween(start, end);
    }

    @Override
    public JobPosting save(JobPosting jobPosting) {
        JobPostingEntity entity = jobPostingJpaRepository.save(JobPostingMapper.toEntity(jobPosting));
        return JobPostingMapper.toDomain(entity);
    }

    @Override
    public void deleteById(Long id) {
        jobPostingJpaRepository.deleteById(id);
    }

    @Override
    public Page<JobPosting> searchWithConditions(
            String search,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String filterType,
            String filter,
            Pageable pageable
    ) {
        QJobPostingEntity jobPosting = QJobPostingEntity.jobPostingEntity;

        BooleanBuilder builder = new BooleanBuilder();

        // 기간 필터
        filterByDate(startDate, endDate, builder, jobPosting);

        // 검색어 (title, addressDetail)
        filterByKeyword(search, builder, jobPosting);

        // ENUM 필터 처리
        filterByColumn(filterType, filter, builder, jobPosting);

        // 정렬
        List<OrderSpecifier<?>> orders = getOrderSpecifiers(pageable);

        List<JobPosting> content = JobPostingMapper.toDomains(queryFactory
                .selectFrom(jobPosting)
                .distinct()
                .leftJoin(jobPosting.workDayTimeEntities, QPostingWorkDayTimeEntity.postingWorkDayTimeEntity).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .fetch());

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
    private void filterByKeyword(String search, BooleanBuilder builder, QJobPostingEntity jobPostingEntity) {
        if (search != null && !search.isBlank()) {
            builder.and(
                    jobPostingEntity.title.containsIgnoreCase(search)
                            .or(jobPostingEntity.addressEntity.addressDetail.containsIgnoreCase(search))
                            .or(jobPostingEntity.addressEntity.region1DepthName.containsIgnoreCase(search))
                            .or(jobPostingEntity.addressEntity.region2DepthName.containsIgnoreCase(search))
                            .or(jobPostingEntity.addressEntity.region3DepthName.containsIgnoreCase(search))
            );
        }
    }

    private void filterByDate(LocalDateTime startDate, LocalDateTime endDate, BooleanBuilder builder, QJobPostingEntity jobPostingEntity) {
        if (startDate != null && endDate != null) {
            builder.and(jobPostingEntity.createdAt.between(startDate, endDate));
        } else if (startDate != null) {
            builder.and(jobPostingEntity.createdAt.goe(startDate));
        } else if (endDate != null) {
            builder.and(jobPostingEntity.createdAt.loe(endDate));
        }
    }


    private void filterByColumn(String filterType, String filter, BooleanBuilder builder, QJobPostingEntity jobPostingEntity) {
        if (filterType != null && filter != null) {
            switch (filterType) {
                case "job_category" -> builder.and(jobPostingEntity.jobCategory.stringValue().eq(filter));
                case "employment_type" -> builder.and(jobPostingEntity.employmentType.stringValue().eq(filter));
                case "gender" -> builder.and(jobPostingEntity.gender.stringValue().eq(filter));
                case "education_level" -> builder.and(jobPostingEntity.educationLevel.stringValue().eq(filter));
                case "visa" -> builder.and(jobPostingEntity.visa.any().stringValue().eq(filter));
            }
        }
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable) {
        QJobPostingEntity jobPosting = QJobPostingEntity.jobPostingEntity;
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        for (Sort.Order order : pageable.getSort()) {
            PathBuilder<JobPostingEntity> path = new PathBuilder<>(JobPostingEntity.class, "jobPosting");

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

