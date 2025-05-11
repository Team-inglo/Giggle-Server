package com.inglo.giggle.owner.application.port.in.query;

import com.inglo.giggle.owner.application.port.in.result.ReadOwnerDetailResult;

import java.util.UUID;

public interface ReadOwnerDetailQuery {

    /**
     * 고용주 상세 조회하기
     *
     * @param accountId 계정 ID
     *
     * @return 고용주 상세 조회하기
     */
    ReadOwnerDetailResult execute(UUID accountId);
}
