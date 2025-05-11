package com.inglo.giggle.user.application.port.in.query;

import com.inglo.giggle.security.info.CustomUserPrincipal;
import com.inglo.giggle.user.application.port.in.result.ReadUserSelfSummaryResult;

import java.util.UUID;

public interface ReadUserSelfSummaryQuery {

    /**
     * 유저 요약 조회하기
     *
     * @param accountId 계정 ID
     *
     * @return 유저 요약 조회하기
     */
    ReadUserSelfSummaryResult execute(CustomUserPrincipal principal, UUID accountId);
}
