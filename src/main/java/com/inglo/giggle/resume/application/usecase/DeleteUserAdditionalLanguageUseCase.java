package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface DeleteUserAdditionalLanguageUseCase {

    /**
     * (유학생) 언어 - ETC 삭제하기 유스케이스
     * @param accountId 계정 ID
     * @param additionalLanguageId 언어- ETC ID
     */
    void execute(UUID accountId, Long additionalLanguageId);
}
