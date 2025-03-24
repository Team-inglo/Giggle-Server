package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.posting.application.dto.response.ReadAdminUserOwnerJobPostingsDigestsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadAdminUserOwnerJobPostingsDigestsUseCase;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingQueryRepository;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
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

        // 날짜 파싱
        LocalDate start = DateTimeUtil.convertStringToLocalDate(stringStartDate);
        LocalDate end = DateTimeUtil.convertStringToLocalDate(stringEndDate);

        LocalDateTime startDate = start.atStartOfDay();
        LocalDateTime endDate = end.plusDays(1).atStartOfDay(); // inclusive

        // 기간 계산
        long periodDays = ChronoUnit.DAYS.between(start, end);

        // 현재 기간 통계 (월별)
        List<UserOwnerJobPostingQueryRepository.GraphStats> currentStats = userOwnerJobPostingRepository.countGraphStatsByMonth(startDate, endDate);

        // 이전 기간 총 지원 수
        int priorTotalCount = userOwnerJobPostingRepository.countByCreatedAtBetween(startDate.minusDays(periodDays + 1), startDate);

        return ReadAdminUserOwnerJobPostingsDigestsResponseDto.of(
                currentStats,
                priorTotalCount
        );
    }

}
