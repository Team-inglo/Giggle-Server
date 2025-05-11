package com.inglo.giggle.document.application.port.in.query;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.port.in.result.ReadIntegratedApplicationDetailResult;

import java.util.UUID;

@UseCase
public interface ReadIntegratedApplicationDetailQuery {

    /**
     * 통합 신청서 상세 조회하기
     *
     * @param accountId 계정 ID
     * @param documentId 문서 ID
     *
     * @return 통합 신청서 상세 조회하기
     */
    ReadIntegratedApplicationDetailResult execute(UUID accountId, Long documentId);
}
