package com.inglo.giggle.document.application.port.in.query;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.port.in.result.ReadOwnerDocumentSummaryResult;

import java.util.UUID;

@UseCase
public interface ReadOwnerDocumentSummaryQuery {

    /**
     * (고용주) 서류 요약 조회하기
     *
     * @param accountId 계정 ID
     * @param userOwnerJobPostingId 유저가 지원한 공고 ID
     *
     * @return (고용주) 서류 요약 조회하기
     */
    ReadOwnerDocumentSummaryResult execute(UUID accountId, Long userOwnerJobPostingId);
}
