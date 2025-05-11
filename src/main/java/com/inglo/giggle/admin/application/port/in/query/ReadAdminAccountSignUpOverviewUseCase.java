package com.inglo.giggle.admin.application.port.in.query;

import com.inglo.giggle.admin.application.port.in.result.ReadAdminAccountSignUpOverviewResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.time.LocalDate;

@UseCase
public interface ReadAdminAccountSignUpOverviewUseCase {

    //TODO: 어디다가 둬야할지 모르겠음

    /**
     * 3.9 (어드민) 가입 회원 통계 조회하기 UseCase
     */
    ReadAdminAccountSignUpOverviewResponseDto execute(LocalDate startDate, LocalDate endDate);
}
