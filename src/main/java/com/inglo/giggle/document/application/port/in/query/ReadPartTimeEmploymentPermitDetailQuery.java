package com.inglo.giggle.document.application.port.in.query;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.port.in.result.ReadPartTimeEmploymentPermitDetailResult;

import java.util.UUID;

@UseCase
public interface ReadPartTimeEmploymentPermitDetailQuery {

    /**
     * 시간제 취업 허가서 상세 조회하기
     *
     * @param accountId 계정 ID
     * @param documentId 문서 ID
     *
     * @return 시간제 취업 허가서 상세 조회하기
     */
    ReadPartTimeEmploymentPermitDetailResult execute(UUID accountId, Long documentId);
}
