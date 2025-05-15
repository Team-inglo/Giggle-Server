package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.UpdateUserResumeRequestDtoV1;
import com.inglo.giggle.resume.application.dto.request.UpdateUserResumeRequestDtoV2;

import java.util.UUID;

@UseCase
public interface UpdateUserResumeUseCase {

    /**
     * (유학생) 자기소개 수정하기 유스케이스
     * @param accountId 계정 ID
     * @param requestDto 자기소개 수정 요청 DTO
     */
    void execute(UUID accountId, UpdateUserResumeRequestDtoV1 requestDto);

    void executeV2(UUID accountId, UpdateUserResumeRequestDtoV2 requestDto);
}
