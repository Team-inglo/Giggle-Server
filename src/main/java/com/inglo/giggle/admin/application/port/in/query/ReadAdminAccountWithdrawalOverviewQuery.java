package com.inglo.giggle.admin.application.port.in.query;

import com.inglo.giggle.admin.application.port.in.result.ReadAdminAccountWithdrawalOverviewResult;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.time.LocalDate;

@UseCase
public interface ReadAdminAccountWithdrawalOverviewQuery {

    //TODO: 어디다가 둬야할지 모르겠음

    /**
     * 3.10 (어드민) 탈퇴 회원 통계 조회하기 UseCase
     */
    ReadAdminAccountWithdrawalOverviewResult execute(LocalDate startDate, LocalDate endDate);
}
