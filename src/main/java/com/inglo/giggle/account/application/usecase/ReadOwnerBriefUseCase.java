package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.presentation.dto.response.ReadOwnerBriefResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface ReadOwnerBriefUseCase {

    /**
     * 고용주 간략 조회하기
     *
     * @param accountId 계정 ID
     *
     * @return 고용주 간략 조회하기
     */
    ReadOwnerBriefResponseDto execute(UUID accountId);
}
