package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.presentation.dto.response.ReadAdminAccountSignUpOverviewResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.time.LocalDate;

@UseCase
public interface ReadAdminAccountSignUpOverviewUseCase {

    /**
     * 3.9 (어드민) 가입 회원 통계 조회하기 UseCase
     */
    ReadAdminAccountSignUpOverviewResponseDto execute(LocalDate startDate, LocalDate endDate);
}
