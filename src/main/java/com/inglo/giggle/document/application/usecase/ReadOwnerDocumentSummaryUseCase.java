package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.response.ReadOwnerDocumentSummaryResponseDto;

import java.util.UUID;

@UseCase
public interface ReadOwnerDocumentSummaryUseCase {

    /**
     * (고용주) 서류 요약 조회하기
     *
     * @param accountId 계정 ID
     * @param userOwnerJobPostingId 유저가 지원한 공고 ID
     *
     * @return (고용주) 서류 요약 조회하기
     */
    ReadOwnerDocumentSummaryResponseDto execute(UUID accountId, Long userOwnerJobPostingId);
}
