package com.inglo.giggle.owner.application.port.in.query;

import com.inglo.giggle.owner.application.port.in.result.ReadOwnerBriefResult;

import java.util.UUID;

public interface ReadOwnerBriefQuery {

    /**
     * 고용주 간략 조회하기
     *
     * @param accountId 계정 ID
     *
     * @return 고용주 간략 조회하기
     */
    ReadOwnerBriefResult execute(UUID accountId);
}
