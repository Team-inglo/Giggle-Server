package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.application.dto.request.JobPostingSearchId;
import com.inglo.giggle.posting.application.dto.request.JobPostingSearchIdRequestDto;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import com.inglo.giggle.posting.domain.type.EWorkingHours;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    List<JobPosting> findAllByOwner(Owner owner);

    @EntityGraph(attributePaths = {"owner"})
    Optional<JobPosting> findWithOwnerById(Long jobPostingId);

    Page<JobPosting> findWithPageByOwner(Owner owner, Pageable pageRequest);

    // 인기순 공고 조회 쿼리
    @Query("SELECT DISTINCT jp.id AS jobPostingId, jp AS jobPosting " +
            "FROM JobPosting jp " +
            "JOIN jp.workDayTimes pw " +
            "WHERE (:jobTitle IS NULL OR jp.title LIKE CONCAT('%', :jobTitle, '%')) " +
            "AND ( " +
            "( " +
            ":region1Depth1 IS NULL AND :region2Depth1 IS NULL AND :region3Depth1 IS NULL AND " +
            ":region1Depth2 IS NULL AND :region2Depth2 IS NULL AND :region3Depth2 IS NULL AND " +
            ":region1Depth3 IS NULL AND :region2Depth3 IS NULL AND :region3Depth3 IS NULL " +
            ") " +
            "OR ( " +
            "(:region1Depth1 IS NOT NULL AND jp.address.region1DepthName = :region1Depth1) " +
            "AND (:region2Depth1 IS NULL OR jp.address.region2DepthName = :region2Depth1) " +
            "AND (:region3Depth1 IS NULL OR jp.address.region3DepthName = :region3Depth1) " +
            ") " +
            "OR ( " +
            "(:region1Depth2 IS NOT NULL AND jp.address.region1DepthName = :region1Depth2) " +
            "AND (:region2Depth2 IS NULL OR jp.address.region2DepthName = :region2Depth2) " +
            "AND (:region3Depth2 IS NULL OR jp.address.region3DepthName = :region3Depth2) " +
            ") " +
            "OR ( " +
            "(:region1Depth3 IS NOT NULL AND jp.address.region1DepthName = :region1Depth3) " +
            "AND (:region2Depth3 IS NULL OR jp.address.region2DepthName = :region2Depth3) " +
            "AND (:region3Depth3 IS NULL OR jp.address.region3DepthName = :region3Depth3) " +
            ")) " +
            "AND (:industryList IS NULL OR jp.jobCategory IN :industryList) " +
            "AND (:workPeriodList IS NULL OR jp.workPeriod IN :workPeriodList) " +
            "AND (:workDaysPerWeekList IS NULL OR " +
            "(SELECT COUNT(wdt) FROM jp.workDayTimes wdt WHERE wdt.dayOfWeek <> :negotiableDay) IN :workDaysPerWeekList) " +
            "AND (:workingDayList IS NULL OR pw.dayOfWeek IN :workingDayList " +
            "OR EXISTS (SELECT 1 FROM jp.workDayTimes wdt WHERE wdt.dayOfWeek = :negotiableDay)) " +
            "AND (:workingHoursList IS NULL OR " +
            "( " +
            ":morningStart BETWEEN pw.workStartTime AND pw.workEndTime OR :morningEnd BETWEEN pw.workStartTime AND pw.workEndTime) AND :morningSelected = TRUE " +
            "OR (:afternoonStart BETWEEN pw.workStartTime AND pw.workEndTime OR :afternoonEnd BETWEEN pw.workStartTime AND pw.workEndTime) AND :afternoonSelected = TRUE " +
            "OR (:eveningStart BETWEEN pw.workStartTime AND pw.workEndTime OR :eveningEnd BETWEEN pw.workStartTime AND pw.workEndTime) AND :eveningSelected = TRUE " +
            "OR (:fullDayStart BETWEEN pw.workStartTime AND pw.workEndTime OR :fullDayEnd BETWEEN pw.workStartTime AND pw.workEndTime) AND :fullDaySelected = TRUE " +
            "OR (:dawnStart BETWEEN pw.workStartTime AND pw.workEndTime OR :dawnEnd BETWEEN pw.workStartTime AND pw.workEndTime) AND :dawnSelected = TRUE " +
            "OR (pw.dayOfWeek = :negotiableDay) " +
            ") " +
            "AND ((:recruitmentPeriod IS NULL) " +
            "OR (:recruitmentPeriod = 'OPENING' AND jp.recruitmentDeadLine >= :today) " +
            "OR (:recruitmentPeriod = 'CLOSED' AND jp.recruitmentDeadLine < :today)) " +
            "AND (:employmentType IS NULL OR jp.employmentType = :employmentType) " +
            "AND (:visa IS NULL OR jp.visa = :visa) " +
            "ORDER BY (SELECT COUNT(b.id) FROM BookMark b WHERE b.jobPosting = jp) DESC")
    Page<JobPostingProjection> findPopularJobPostingsWithFilters(
            @Param("jobTitle") String jobTitle,
            @Param("region1Depth1") String region1Depth1,
            @Param("region1Depth2") String region1Depth2,
            @Param("region1Depth3") String region1Depth3,
            @Param("region2Depth1") String region2Depth1,
            @Param("region2Depth2") String region2Depth2,
            @Param("region2Depth3") String region2Depth3,
            @Param("region3Depth1") String region3Depth1,
            @Param("region3Depth2") String region3Depth2,
            @Param("region3Depth3") String region3Depth3,
            @Param("industryList") List<EJobCategory> industryList,
            @Param("workPeriodList") List<EWorkPeriod> workPeriodList,
            @Param("workDaysPerWeekList") List<Integer> workDaysPerWeekList,
            @Param("workingDayList") List<EDayOfWeek> workingDayList,
            @Param("workingHoursList") List<EWorkingHours> workingHoursList,
            @Param("morningStart") LocalTime morningStart,
            @Param("morningEnd") LocalTime morningEnd,
            @Param("afternoonStart") LocalTime afternoonStart,
            @Param("afternoonEnd") LocalTime afternoonEnd,
            @Param("eveningStart") LocalTime eveningStart,
            @Param("eveningEnd") LocalTime eveningEnd,
            @Param("fullDayStart") LocalTime fullDayStart,
            @Param("fullDayEnd") LocalTime fullDayEnd,
            @Param("dawnStart") LocalTime dawnStart,
            @Param("dawnEnd") LocalTime dawnEnd,
            @Param("morningSelected") boolean morningSelected,
            @Param("afternoonSelected") boolean afternoonSelected,
            @Param("eveningSelected") boolean eveningSelected,
            @Param("fullDaySelected") boolean fullDaySelected,
            @Param("dawnSelected") boolean dawnSelected,
            @Param("negotiableDay") EDayOfWeek negotiableDay,
            @Param("today") LocalDate today,
            @Param("recruitmentPeriod") String recruitmentPeriod,
            @Param("employmentType") EEmploymentType employmentType,
            @Param("visa") EVisa visa,
            Pageable pageable
    );

    // 최신순 공고 조회 쿼리
    @Query("SELECT DISTINCT jp.id AS jobPostingId, jp AS jobPosting " +
            "FROM JobPosting jp " +
            "JOIN jp.workDayTimes pw " +
            "WHERE (:jobTitle IS NULL OR jp.title LIKE CONCAT('%', :jobTitle, '%')) " +
            "AND ( " +
            "( " +
            ":region1Depth1 IS NULL AND :region2Depth1 IS NULL AND :region3Depth1 IS NULL AND " +
            ":region1Depth2 IS NULL AND :region2Depth2 IS NULL AND :region3Depth2 IS NULL AND " +
            ":region1Depth3 IS NULL AND :region2Depth3 IS NULL AND :region3Depth3 IS NULL " +
            ") " +
            "OR ( " +
            "(:region1Depth1 IS NOT NULL AND jp.address.region1DepthName = :region1Depth1) " +
            "AND (:region2Depth1 IS NULL OR jp.address.region2DepthName = :region2Depth1) " +
            "AND (:region3Depth1 IS NULL OR jp.address.region3DepthName = :region3Depth1) " +
            ") " +
            "OR ( " +
            "(:region1Depth2 IS NOT NULL AND jp.address.region1DepthName = :region1Depth2) " +
            "AND (:region2Depth2 IS NULL OR jp.address.region2DepthName = :region2Depth2) " +
            "AND (:region3Depth2 IS NULL OR jp.address.region3DepthName = :region3Depth2) " +
            ") " +
            "OR ( " +
            "(:region1Depth3 IS NOT NULL AND jp.address.region1DepthName = :region1Depth3) " +
            "AND (:region2Depth3 IS NULL OR jp.address.region2DepthName = :region2Depth3) " +
            "AND (:region3Depth3 IS NULL OR jp.address.region3DepthName = :region3Depth3) " +
            ")) " +
            "AND (:industryList IS NULL OR jp.jobCategory IN :industryList) " +
            "AND ((:workPeriodList IS NULL OR jp.workPeriod IN :workPeriodList)) " +
            "AND ((" +
            "(:workDaysPerWeekList IS NULL OR " +
            "(:workDaysPerWeekList IS NOT NULL AND " +
            "(SELECT COUNT(wdt) FROM jp.workDayTimes wdt WHERE wdt.dayOfWeek <> :negotiableDay) IN :workDaysPerWeekList) " +
            ") " +
            "OR EXISTS (SELECT 1 FROM jp.workDayTimes wdt WHERE wdt.dayOfWeek = :negotiableDay)) " +
            ")" +
            "AND (:workingDayList IS NULL OR pw.dayOfWeek IN :workingDayList " +
            "OR EXISTS (SELECT 1 FROM jp.workDayTimes wdt WHERE wdt.dayOfWeek = :negotiableDay)) " +
            "AND (:workingHoursList IS NULL OR " +
            "( " +
            ":morningStart BETWEEN pw.workStartTime AND pw.workEndTime OR :morningEnd BETWEEN pw.workStartTime AND pw.workEndTime) AND :morningSelected = TRUE " +
            "OR (:afternoonStart BETWEEN pw.workStartTime AND pw.workEndTime OR :afternoonEnd BETWEEN pw.workStartTime AND pw.workEndTime) AND :afternoonSelected = TRUE " +
            "OR (:eveningStart BETWEEN pw.workStartTime AND pw.workEndTime OR :eveningEnd BETWEEN pw.workStartTime AND pw.workEndTime) AND :eveningSelected = TRUE " +
            "OR (:fullDayStart BETWEEN pw.workStartTime AND pw.workEndTime OR :fullDayEnd BETWEEN pw.workStartTime AND pw.workEndTime) AND :fullDaySelected = TRUE " +
            "OR (:dawnStart BETWEEN pw.workStartTime AND pw.workEndTime OR :dawnEnd BETWEEN pw.workStartTime AND pw.workEndTime) AND :dawnSelected = TRUE " +
            "OR (pw.dayOfWeek = :negotiableDay) " +
            ") "+
            "AND ((:recruitmentPeriod IS NULL) " +
            "OR (:recruitmentPeriod = 'OPENING' AND jp.recruitmentDeadLine >= :today) " +
            "OR (:recruitmentPeriod = 'CLOSED' AND jp.recruitmentDeadLine < :today)) " +
            "AND (:employmentType IS NULL OR jp.employmentType = :employmentType) " +
            "AND (:visa IS NULL OR jp.visa = :visa) " +
            "ORDER BY jp.createdAt DESC"
    )
    Page<JobPostingProjection> findRecentJobPostingsWithFilters(
            @Param("jobTitle") String jobTitle,
            @Param("region1Depth1") String region1Depth1,
            @Param("region1Depth2") String region1Depth2,
            @Param("region1Depth3") String region1Depth3,
            @Param("region2Depth1") String region2Depth1,
            @Param("region2Depth2") String region2Depth2,
            @Param("region2Depth3") String region2Depth3,
            @Param("region3Depth1") String region3Depth1,
            @Param("region3Depth2") String region3Depth2,
            @Param("region3Depth3") String region3Depth3,
            @Param("industryList") List<EJobCategory> industryList,
            @Param("workPeriodList") List<EWorkPeriod> workPeriodList,
            @Param("workDaysPerWeekList") List<Integer> workDaysPerWeekList,
            @Param("workingDayList") List<EDayOfWeek> workingDayList,
            @Param("workingHoursList") List<EWorkingHours> workingHoursList,
            @Param("morningStart") LocalTime morningStart,
            @Param("morningEnd") LocalTime morningEnd,
            @Param("afternoonStart") LocalTime afternoonStart,
            @Param("afternoonEnd") LocalTime afternoonEnd,
            @Param("eveningStart") LocalTime eveningStart,
            @Param("eveningEnd") LocalTime eveningEnd,
            @Param("fullDayStart") LocalTime fullDayStart,
            @Param("fullDayEnd") LocalTime fullDayEnd,
            @Param("dawnStart") LocalTime dawnStart,
            @Param("dawnEnd") LocalTime dawnEnd,
            @Param("morningSelected") boolean morningSelected,
            @Param("afternoonSelected") boolean afternoonSelected,
            @Param("eveningSelected") boolean eveningSelected,
            @Param("fullDaySelected") boolean fullDaySelected,
            @Param("dawnSelected") boolean dawnSelected,
            @Param("negotiableDay") EDayOfWeek negotiableDay,
            @Param("today") LocalDate today,
            @Param("recruitmentPeriod") String recruitmentPeriod,
            @Param("employmentType") EEmploymentType employmentType,
            @Param("visa") EVisa visa,
            @Param("pageable") Pageable pageable
    );

    @Query("SELECT jp FROM JobPosting jp " +
            "JOIN FETCH jp.owner o " +
            "JOIN FETCH jp.workDayTimes wdt " +
            "WHERE jp.id IN :jobPostingIds")
    List<JobPosting> findJobPostingsWithDetailsByIds(
            @Param("jobPostingIds") List<Long> jobPostingIds
    );


    @Query("SELECT jp FROM JobPosting jp " +
            "LEFT JOIN FETCH jp.owner o " +
            "LEFT JOIN FETCH jp.workDayTimes wd " +
            "LEFT JOIN UserOwnerJobPosting uojp ON uojp.jobPosting = jp " +
            "WHERE jp.recruitmentDeadLine is null OR jp.recruitmentDeadLine >= :today " +
            "GROUP BY jp.id, o.id, wd.id " +
            "ORDER BY COUNT(uojp.id) DESC")
    List<JobPosting> findTrendingJobPostingsWithFetchJoin(@Param("today") LocalDate today);

    @Query("SELECT DISTINCT jp FROM JobPosting jp " +
            "LEFT JOIN FETCH jp.owner " +
            "LEFT JOIN FETCH jp.workDayTimes " +
            "WHERE jp.recruitmentDeadLine is null OR jp.recruitmentDeadLine >= :today " +
            "ORDER BY jp.createdAt DESC")
    List<JobPosting> findRecentlyJobPostingsWithFetchJoin(@Param("today") LocalDate today);

    @Query("SELECT jp FROM JobPosting jp " +
            "JOIN FETCH jp.owner o " +
            "JOIN FETCH jp.workDayTimes wd " +
            "JOIN BookMark bm ON bm.jobPosting = jp " +
            "WHERE bm.user.id = :accountId " +
            "AND (jp.recruitmentDeadLine is null OR jp.recruitmentDeadLine >= :today) " +
            "GROUP BY jp.id, o.id, wd.id " +
            "ORDER BY COUNT(bm.id) DESC")
    List<JobPosting> findBookmarkedJobPostingsWithFetchJoin(
            @Param("accountId") UUID accountId,
            @Param("today") LocalDate today);


    @Query("SELECT b.jobPosting.id, COUNT(b) FROM BookMark b WHERE b.jobPosting.id IN :jobPostingIds GROUP BY b.jobPosting.id")
    List<Object[]> countBookmarksByJobPostingIds(@Param("jobPostingIds") List<Long> jobPostingIds);

    public interface JobPostingProjection {
        Long getJobPostingId();
        UUID getOwnerId();
        List<Long> getWorkDayTimeIds();
    }

}

