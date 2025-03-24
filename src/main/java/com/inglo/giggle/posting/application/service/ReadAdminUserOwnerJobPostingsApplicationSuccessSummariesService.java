package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.posting.application.dto.response.ReadAdminUserOwnerJobPostingsApplicationSuccessSummariesResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadAdminUserOwnerJobPostingsApplicationSuccessSummariesUseCase;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReadAdminUserOwnerJobPostingsApplicationSuccessSummariesService implements ReadAdminUserOwnerJobPostingsApplicationSuccessSummariesUseCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    @Override
    public ReadAdminUserOwnerJobPostingsApplicationSuccessSummariesResponseDto execute(String stringStartDate, String stringEndDate) {

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

        // 기간 내 공고 등록 수 조회
        int currentCount = getJobPostingsCountsByDaysBetween(startDate, endDate.plusDays(1));
        long days = getDays(startDate, endDate);
        int priorCount = getJobPostingsCountsByDaysBetween(startDate.minusDays(days), startDate);

        // 전기간 대비 등록 수 비율 계산
        double comparisonRate = getComparisonRate(priorCount, currentCount);

        // 응답 DTO 생성
        return ReadAdminUserOwnerJobPostingsApplicationSuccessSummariesResponseDto.of(
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
        return userOwnerJobPostingRepository.countUserOwnerJobPostingByStepAndCreatedAtBetween(
                EApplicationStep.APPLICATION_SUCCESS,
                startDate.atStartOfDay(),
                endDate.atStartOfDay()
        );
    }
}
