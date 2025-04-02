package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.presentation.dto.response.ReadPartTimeEmploymentPermitDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadPartTimeEmploymentPermitDetailUseCase {

    /**
     * 시간제 취업 허가서 상세 조회하기
     *
     * @param accountId 계정 ID
     * @param documentId 문서 ID
     *
     * @return 시간제 취업 허가서 상세 조회하기
     */
    ReadPartTimeEmploymentPermitDetailResponseDto execute(UUID accountId, Long documentId);
}
