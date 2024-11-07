package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.response.ReadUserWorkExperienceDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserWorkExperienceDetailUseCase {

    /**
     * (유학생) 경력 상세 조회하기 유스케이스
     * @param accountId 계정 ID
     * @param workExperienceId 경력 ID
     * @return 경력 상세 조회하기 응답 DTO
     */
    ReadUserWorkExperienceDetailResponseDto execute(UUID accountId, Long workExperienceId);
}
