package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.presentation.dto.request.CreateUserWorkExperienceRequestDto;

import java.util.UUID;

@UseCase
public interface CreateUserWorkExperienceUseCase {

    /**
     * (유학생) 경력 추가하기 유스케이스
     * @param accountId 계정 ID
     * @param requestDto 경력 추가하기 요청 DTO
     */
    void execute(UUID accountId, CreateUserWorkExperienceRequestDto requestDto);
}
