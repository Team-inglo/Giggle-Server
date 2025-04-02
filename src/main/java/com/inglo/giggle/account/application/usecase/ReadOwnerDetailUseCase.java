package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.presentation.dto.response.ReadOwnerDetailResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface ReadOwnerDetailUseCase {

    /**
     * 고용주 상세 조회하기
     *
     * @param accountId 계정 ID
     *
     * @return 고용주 상세 조회하기
     */
    ReadOwnerDetailResponseDto execute(UUID accountId);
}
