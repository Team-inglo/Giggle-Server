package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.posting.application.dto.response.ReadAdminJobPostingsOverviewsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadAdminJobPostingsOverviewsUseCase;
import com.inglo.giggle.posting.repository.mysql.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadAdminJobPostingsOverviewsService implements ReadAdminJobPostingsOverviewsUseCase {

    private final JobPostingRepository jobPostingRepository;

    @Override
    public ReadAdminJobPostingsOverviewsResponseDto execute(UUID accountId, String stringStartDate, String stringEndDate) {

        // stringStartDate, stringEndDate를 LocalDate로 변환
        LocalDate startDate = DateTimeUtil.convertStringToLocalDate(stringStartDate);
        LocalDate endDate = DateTimeUtil.convertStringToLocalDate(stringEndDate);

        // 기간 내 공고 등록 수 조회
        int currentCount = getJobPostingsCountsByDaysBetween(startDate, endDate.plusDays(1));
        long days = getDays(startDate, endDate);
        int priorCount = getJobPostingsCountsByDaysBetween(startDate.minusDays(days), startDate);

        // 전기간 대비 등록 수 비율 계산
        double comparisonRate = getComparisonRate(priorCount, currentCount);

        // 응답 DTO 생성
        return ReadAdminJobPostingsOverviewsResponseDto.of(
                currentCount,
                priorCount,
                comparisonRate
        );
    }

    /* -------------------------------------------- */
    /* Private Methods ---------------------------- */
    /* -------------------------------------------- */
    private double getComparisonRate(int priorCount, int currentCount) {
        double comparisonRate;
        if (priorCount == 0) {
            comparisonRate = currentCount > 0 ? 100.0 : 0.0;
        } else {
            comparisonRate = ((double) (currentCount - priorCount) / priorCount) * 100.0;
        }
        return Math.round(comparisonRate * 100.0) / 100.0;
    }

    private static long getDays(LocalDate startDate, LocalDate endDate) {
        return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate.plusDays(1));
    }

    private int getJobPostingsCountsByDaysBetween(LocalDate startDate, LocalDate endDate) {
        return jobPostingRepository.countByCreatedAtBetween(startDate.atStartOfDay(), endDate.atStartOfDay());
    }
}
