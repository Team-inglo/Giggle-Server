package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.response.ReadOwnerResumeDetailByResumeIdResponseDto;
import com.inglo.giggle.resume.application.dto.response.ReadOwnerResumeDetailResponseDtoV1;
import com.inglo.giggle.resume.application.dto.response.ReadOwnerResumeDetailResponseDtoV2;

import java.util.UUID;

@UseCase
public interface ReadOwnerResumeDetailUseCase {

    /**
     * (고용주) 이력서 상세 조회하기 유스케이스
     * @param accountId 계정 ID
     * @param userOwnerJobPostingId 이력서 ID
     * @return 이력서 상세 조회하기 응답 DTO
     */
    ReadOwnerResumeDetailResponseDtoV1 execute(UUID accountId, Long userOwnerJobPostingId);

    ReadOwnerResumeDetailResponseDtoV2 executeV2(UUID accountId, Long userOwnerJobPostingId);

    ReadOwnerResumeDetailByResumeIdResponseDto execute(UUID accountId, UUID resumeId);
}
