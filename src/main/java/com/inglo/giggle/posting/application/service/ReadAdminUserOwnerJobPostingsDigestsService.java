package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.posting.application.dto.response.ReadAdminUserOwnerJobPostingsDigestsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadAdminUserOwnerJobPostingsDigestsUseCase;
import com.inglo.giggle.posting.repository.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAdminUserOwnerJobPostingsDigestsService implements ReadAdminUserOwnerJobPostingsDigestsUseCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadAdminUserOwnerJobPostingsDigestsResponseDto execute(String stringStartDate, String stringEndDate) {

        LocalDateTime startDate;
        LocalDateTime endDate;

        // 시작일과 종료일 모두 있는 경우
        if (stringStartDate != null && stringEndDate != null) {
            LocalDate start = DateTimeUtil.convertStringToLocalDate(stringStartDate);
            LocalDate end = DateTimeUtil.convertStringToLocalDate(stringEndDate);
            startDate = start.atStartOfDay();
            endDate = end.plusDays(1).atStartOfDay();

            // 시작일만 있는 경우
        } else if (stringStartDate != null) {
            LocalDate start = DateTimeUtil.convertStringToLocalDate(stringStartDate);
            startDate = start.atStartOfDay();
            endDate = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.DAYS); // 현재까지

            // 종료일만 있는 경우
        } else if (stringEndDate != null) {
            LocalDate end = DateTimeUtil.convertStringToLocalDate(stringEndDate);
            startDate = LocalDate.of(2025, 3, 24).atStartOfDay();
            endDate = end.plusDays(1).atStartOfDay();

            // 둘 다 없는 경우 (전체 기간)
        } else {
            startDate = LocalDate.of(2025, 3, 24).atStartOfDay(); // 과거부터
            endDate = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.DAYS); // 현재까지
        }

        long periodDays = ChronoUnit.DAYS.between(startDate.toLocalDate(), endDate.toLocalDate()) - 1;

        List<UserOwnerJobPostingRepository.GraphStats> currentStats =
                userOwnerJobPostingRepository.countGraphStatsByMonth(startDate, endDate);

        int priorTotalCount =
                userOwnerJobPostingRepository.countByCreatedAtBetween(startDate.minusDays(periodDays + 1), startDate);

        return ReadAdminUserOwnerJobPostingsDigestsResponseDto.of(currentStats, priorTotalCount);
    }
}
