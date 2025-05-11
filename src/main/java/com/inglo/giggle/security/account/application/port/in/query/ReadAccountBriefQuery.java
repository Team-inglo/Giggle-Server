package com.inglo.giggle.security.account.application.port.in.query;

import com.inglo.giggle.security.account.application.port.in.result.ReadAccountBriefResult;

import java.util.UUID;

public interface ReadAccountBriefQuery {

        /**
        * 사용자 간단 정보 읽기
        * @param accountId 계정 ID
        */
        ReadAccountBriefResult execute(UUID accountId);
}
