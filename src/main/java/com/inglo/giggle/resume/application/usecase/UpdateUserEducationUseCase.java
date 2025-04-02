package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.presentation.dto.request.UpdateUserEducationRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateUserEducationUseCase {

    /**
     * (유학생) 학력 수정하기 유스케이스
     * @param accountId 계정 ID
     * @param educationId 학력 ID
     */
    void execute(UUID accountId, Long educationId, UpdateUserEducationRequestDto requestDto);
}
