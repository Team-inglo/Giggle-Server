package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.presentation.dto.response.ReadUserBookMarkCountResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserBookMarkCountUseCase {

    /**
     * 유학생이 북마크 현황(개수) 확인합니다.
     *
     * @param accountId 계정 ID
     *
     * @return 북마크 조회 응답 DTO
     */
    ReadUserBookMarkCountResponseDto execute(
            UUID accountId
    );
}
