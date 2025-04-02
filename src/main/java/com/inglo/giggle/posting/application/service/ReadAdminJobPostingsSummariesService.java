package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.posting.presentation.dto.response.ReadAdminJobPostingsSummariesResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadAdminJobPostingsSummariesUseCase;
import com.inglo.giggle.posting.persistence.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadAdminJobPostingsSummariesService implements ReadAdminJobPostingsSummariesUseCase {

    private final JobPostingRepository jobPostingRepository;

    @Override
    public ReadAdminJobPostingsSummariesResponseDto execute(UUID accountId, String stringStartDate, String stringEndDate) {

        // stringStartDate, stringEndDate를 LocalDate로 변환
        LocalDate startDate;
        LocalDate endDate;

        if (stringStartDate != null && stringEndDate != null) {
            startDate = DateTimeUtil.convertStringToLocalDate(stringStartDate);
            endDate = DateTimeUtil.convertStringToLocalDate(stringEndDate);
        } else if (stringStartDate != null) {
            startDate = DateTimeUtil.convertStringToLocalDate(stringStartDate);
            endDate = LocalDate.now();
        } else if (stringEndDate != null) {
            endDate = DateTimeUtil.convertStringToLocalDate(stringEndDate);
            startDate = LocalDate.of(2025, 3, 24);
        } else {
            startDate = LocalDate.of(2025, 3, 24);
            endDate = LocalDate.now();
        }

        int currentCount = getJobPostingsCountsByDaysBetween(startDate, endDate.plusDays(1));
        long days = getDays(startDate, endDate);
        int priorCount = getJobPostingsCountsByDaysBetween(startDate.minusDays(days), startDate);

        double comparisonRate = getComparisonRate(priorCount, currentCount);

        return ReadAdminJobPostingsSummariesResponseDto.of(
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
