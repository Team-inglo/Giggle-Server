package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface DeleteUserWorkExperienceUseCase {

    /**
     * (유학생) 경력 삭제하기 유스케이스
     * @param accountId 계정 ID
     * @param workExperienceId 경력 ID
     */
    void execute(UUID accountId, Long workExperienceId);
}
