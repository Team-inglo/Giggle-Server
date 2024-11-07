package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.application.dto.response.ReadUserDetailResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface ReadUserDetailUseCase {

    /**
     * 유저 상세 조회하기
     *
     * @param accountId 계정 ID
     *
     * @return 유저 상세 조회하기
     */
    ReadUserDetailResponseDto execute(UUID accountId);
}
