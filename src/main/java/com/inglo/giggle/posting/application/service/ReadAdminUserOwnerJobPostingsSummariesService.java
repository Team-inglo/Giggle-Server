package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.posting.application.dto.response.ReadAdminUserOwnerJobPostingsSummariesResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadAdminUserOwnerJobPostingsSummariesUseCase;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReadAdminUserOwnerJobPostingsSummariesService implements ReadAdminUserOwnerJobPostingsSummariesUseCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    @Override
    public ReadAdminUserOwnerJobPostingsSummariesResponseDto execute(
            String stringStartDate,
            String stringEndDate
    ) {

        // stringStartDate, stringEndDate를 LocalDate로 변환
        LocalDate localDate = DateTimeUtil.convertStringToLocalDate(stringStartDate);
        LocalDate endDate = DateTimeUtil.convertStringToLocalDate(stringEndDate);

        // 기간 내 지원 수 조회
        int currentCount = getJobPostingsCountsByDaysBetween(localDate, endDate.plusDays(1));
        long days = getDays(localDate, endDate);
        int priorCount = getJobPostingsCountsByDaysBetween(localDate.minusDays(days), localDate);

        // 전기간 대비 등록 수 비율 계산
        double comparisonRate = getComparisonRate(priorCount, currentCount);

        // 응답 DTO 생성
        return ReadAdminUserOwnerJobPostingsSummariesResponseDto.of(
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
        return userOwnerJobPostingRepository.countByCreatedAtBetween(startDate.atStartOfDay(), endDate.atStartOfDay());
    }
}
