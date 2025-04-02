package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.presentation.dto.request.UpdateUserWorkExperienceRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateUserWorkExperienceUseCase {

    /**
     * (유학생) 경력 수정하기 유스케이스
     * @param accountId 계정 ID
     * @param workExperienceId 경력 ID
     * @param requestDto 경력 수정 요청 DTO
     */
    void execute(UUID accountId, Long workExperienceId, UpdateUserWorkExperienceRequestDto requestDto);
}
