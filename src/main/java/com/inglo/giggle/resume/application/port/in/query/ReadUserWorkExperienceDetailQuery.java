package com.inglo.giggle.resume.application.port.in.query;

import com.inglo.giggle.resume.application.port.in.result.ReadUserWorkExperienceDetailResult;

import java.util.UUID;

public interface ReadUserWorkExperienceDetailQuery {

    /**
     * (유학생) 경력 상세 조회하기 유스케이스
     * @param accountId 계정 ID
     * @param workExperienceId 경력 ID
     * @return 경력 상세 조회하기 응답 DTO
     */
    ReadUserWorkExperienceDetailResult execute(UUID accountId, Long workExperienceId);
}
