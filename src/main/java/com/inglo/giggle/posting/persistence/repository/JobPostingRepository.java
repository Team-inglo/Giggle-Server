package com.inglo.giggle.posting.persistence.repository;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import com.inglo.giggle.posting.domain.type.EWorkingHours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface JobPostingRepository {

    JobPosting findByIdOrElseThrow(Long id);

    List<JobPosting> findAll();

    List<JobPosting> findAllByOwner(Owner owner);

    JobPosting findWithOwnerByIdOrElseThrow(Long jobPostingId);

    Page<JobPosting> findWithPageByOwner(Owner owner, Pageable pageRequest);

    // 인기순 공고 조회 쿼리
    Page<JobPostingProjection> findPopularJobPostingsWithFilters(
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
    );

    // 최신순 공고 조회 쿼리
    Page<JobPostingProjection> findRecentJobPostingsWithFilters(
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
    );

    List<JobPosting> findJobPostingsWithDetailsByIds(
            List<Long> jobPostingIds
    );

    List<JobPosting> findJobPostingsWithCompanyImagesByIds(
            List<Long> jobPostingIds
    );

    Page<JobPostingProjection> findTrendingJobPostingsWithFetchJoin(
            LocalDate today,
            Pageable pageable
    );

    Page<JobPostingProjection> findRecentlyJobPostingsWithFetchJoin(
            LocalDate today,
            Pageable pageable
    );

    Page<JobPostingProjection> findBookmarkedJobPostingsWithFetchJoin(
            UUID accountId,
            LocalDate today,
            Pageable pageable
    );

    List<Object[]> countBookmarksByJobPostingIdsAndAccountId(List<Long> jobPostingIds, UUID accountId);

    int countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    JobPosting save(JobPosting jobPosting);

    void deleteById(Long id);

    Page<JobPosting> searchWithConditions(
            String search,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String filterType,
            String filter,
            Pageable pageable
    );

    interface JobPostingProjection {
        Long getJobPostingId();
        UUID getOwnerId();
        List<Long> getWorkDayTimeIds();
    }
}

