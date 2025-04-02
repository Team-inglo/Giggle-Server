package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.presentation.dto.response.ReadIntegratedApplicationDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadIntegratedApplicationDetailUseCase {

    /**
     * 통합 신청서 상세 조회하기
     *
     * @param accountId 계정 ID
     * @param documentId 문서 ID
     *
     * @return 통합 신청서 상세 조회하기
     */
    ReadIntegratedApplicationDetailResponseDto execute(UUID accountId, Long documentId);
}
