package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.application.dto.response.ReadUserSummaryResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface ReadUserSummaryUseCase {

    /**
     * 유저 요약 조회하기
     *
     * @param accountId 계정 ID
     *
     * @return 유저 요약 조회하기
     */
    ReadUserSummaryResponseDto execute(UUID accountId);
}
