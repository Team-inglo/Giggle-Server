package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.response.ReadUserResumeDetailResponseDtoV1;
import com.inglo.giggle.resume.application.dto.response.ReadUserResumeDetailResponseDtoV2;

import java.util.UUID;

@UseCase
public interface ReadUserResumeDetailUseCase {

    /**
     * (유학생) 이력서 상세 조회하기 유스케이스
     * @param accountId 계정 ID
     */
    ReadUserResumeDetailResponseDtoV1 execute(UUID accountId);

    ReadUserResumeDetailResponseDtoV2 executeV2(UUID accountId);
}
