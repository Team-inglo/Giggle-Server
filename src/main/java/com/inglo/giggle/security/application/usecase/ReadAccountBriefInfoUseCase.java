package com.inglo.giggle.security.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.presentation.dto.response.AccountBriefInfoResponseDto;

import java.util.UUID;

@UseCase
public interface ReadAccountBriefInfoUseCase {

        /**
        * 사용자 간단 정보 읽기
        * @param accountId 계정 ID
        */
        AccountBriefInfoResponseDto execute(UUID accountId);
}
