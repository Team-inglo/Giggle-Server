package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.presentation.dto.request.CreateUserEducationRequestDto;

import java.util.UUID;

@UseCase
public interface CreateUserEducationUseCase {

    /**
     * (유학생) 학력 생성하기 유스케이스
     * @param accountId 계정 ID
     * @param requestDto 학력 생성하기 요청 DTO
     */
    void execute(UUID accountId, CreateUserEducationRequestDto requestDto);
}
