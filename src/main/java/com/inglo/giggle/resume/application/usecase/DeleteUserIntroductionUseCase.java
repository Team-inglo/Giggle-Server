package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface DeleteUserIntroductionUseCase {

    /**
     * (유학생) 자기소개 삭제하기 유스케이스
     * @param accountId 계정 ID
     */
    void execute(UUID accountId);
}
