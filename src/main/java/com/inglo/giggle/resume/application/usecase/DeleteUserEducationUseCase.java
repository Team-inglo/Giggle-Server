package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface DeleteUserEducationUseCase {

    /**
     * (유학생) 학력 삭제하기 유스케이스
     * @param accountId 계정 ID
     * @param educationId 학력 ID
     */
    void execute(UUID accountId, Long educationId);
}
