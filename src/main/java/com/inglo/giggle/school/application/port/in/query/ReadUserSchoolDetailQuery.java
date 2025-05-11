package com.inglo.giggle.school.application.port.in.query;

import com.inglo.giggle.school.application.port.in.result.ReadUserSchoolDetailResult;
import com.inglo.giggle.security.info.CustomUserPrincipal;

import java.util.UUID;

public interface ReadUserSchoolDetailQuery {

    /**
     * 유저 학교 정보 상세 조회 쿼리
     * @param accountId 계정 ID
     * @return ReadUserSchoolDetailResponseDto 유저 학교 상세정보 DTO
     */
    ReadUserSchoolDetailResult execute(UUID accountId);
}
