package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.application.dto.response.ReadAdminAccountWithdrawalOverviewResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.time.LocalDate;

@UseCase
public interface ReadAdminAccountWithdrawalOverviewUseCase {

    /**
     * 3.10 (어드민) 탈퇴 회원 통계 조회하기 UseCase
     */
    ReadAdminAccountWithdrawalOverviewResponseDto execute(LocalDate startDate, LocalDate endDate);
}
