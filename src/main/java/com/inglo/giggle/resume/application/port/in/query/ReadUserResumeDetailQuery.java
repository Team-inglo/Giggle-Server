package com.inglo.giggle.resume.application.port.in.query;

import com.inglo.giggle.resume.application.port.in.result.ReadUserResumeDetailResult;
import com.inglo.giggle.security.info.CustomUserPrincipal;

import java.util.UUID;

public interface ReadUserResumeDetailQuery {

    /**
     * (유학생) 이력서 상세 조회하기 유스케이스
     * @param accountId 계정 ID
     */
    ReadUserResumeDetailResult execute(UUID accountId);
}
