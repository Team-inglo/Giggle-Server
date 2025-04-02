package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.presentation.dto.response.ReadUserDocumentSummaryResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserDocumentSummaryUseCase {

    /**
     * (유학생) 서류 요약 조회하기
     *
     * @param accountId 계정 ID
     * @param userOwnerJobPostingId 유저가 지원한 공고 ID
     *
     * @return (유학생) 서류 요약 조회하기
     */
    ReadUserDocumentSummaryResponseDto readUserDocumentSummary(UUID accountId, Long userOwnerJobPostingId);
}
