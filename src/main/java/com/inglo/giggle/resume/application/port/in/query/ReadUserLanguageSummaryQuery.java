package com.inglo.giggle.resume.application.port.in.query;

import com.inglo.giggle.resume.application.port.in.result.ReadUserLanguageSummaryResult;

import java.util.UUID;

public interface ReadUserLanguageSummaryQuery {

    /**
     * (유학생) 언어 요약 조회하기 유스케이스
     * @param accountId 계정 ID
     * @return 언어 요약 조회하기 응답 DTO
     */
    ReadUserLanguageSummaryResult execute(UUID accountId);
}
