package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface DeleteUserWorkPreferenceUseCase {

    /**
     * 7.24 (유학생) 희망 근로 조건 삭제하기
     */
    void execute(UUID accountId, Long workPreferenceId);
}
