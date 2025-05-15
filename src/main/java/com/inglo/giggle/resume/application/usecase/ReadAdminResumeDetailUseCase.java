package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.response.ReadAdminResumeDetailResponseDtoV1;
import com.inglo.giggle.resume.application.dto.response.ReadAdminResumeDetailResponseDtoV2;

import java.util.UUID;

@UseCase
public interface ReadAdminResumeDetailUseCase {
    /**
     * 7.20 (어드민) 이력서 조회하기
     */
    ReadAdminResumeDetailResponseDtoV1 execute(UUID resumeId);

    ReadAdminResumeDetailResponseDtoV2 executeV2(UUID resumeId);
}
