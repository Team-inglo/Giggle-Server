package com.inglo.giggle.resume.application.port.in.query;

import com.inglo.giggle.resume.application.port.in.result.ReadUserEducationDetailResult;

import java.util.UUID;

public interface ReadUserEducationDetailQuery {

    /**
     * (유학생) 학력 상세 조회하기 유스케이스
     * @param accountId 계정 ID
     * @param educationId 학력 ID
     * @return 학력 상세 조회하기 응답 DTO
     */
    ReadUserEducationDetailResult execute(UUID accountId, Long educationId);

}
