package com.inglo.giggle.user.application.port.in.query;

import com.inglo.giggle.user.application.port.in.result.ReadUserSelfDetailResult;

import java.util.UUID;

public interface ReadUserSelfDetailQuery {

    /**
     * 유저 상세 조회하기
     *
     * @param accountId 계정 ID
     *
     * @return 유저 상세 조회하기
     */
    ReadUserSelfDetailResult execute(UUID accountId);
}
