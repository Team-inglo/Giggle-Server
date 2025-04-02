package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.presentation.dto.response.ReadUserOwnerJobPostingCountResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserUserOwnerJobPostingCountUseCase {
    /**
     * 유저가 지원 현황(개수) 확인하기
     *
     * @param accountId 계정 ID

     *
     * @return 유저가 지원 현황(개수) 확인하기
     */
    ReadUserOwnerJobPostingCountResponseDto execute(
            UUID accountId
    );
}
