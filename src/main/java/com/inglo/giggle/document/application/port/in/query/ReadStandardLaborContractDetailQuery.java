package com.inglo.giggle.document.application.port.in.query;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.port.in.result.ReadStandardLaborContractDetailResult;

import java.util.UUID;

@UseCase
public interface ReadStandardLaborContractDetailQuery {

    /**
     * 표준 근로계약서 상세 조회하기
     *
     * @param accountId 계정 ID
     * @param documentId 문서 ID
     *
     * @return 표준 근로계약서 상세 조회하기
     */
    ReadStandardLaborContractDetailResult execute(UUID accountId, Long documentId);
}
