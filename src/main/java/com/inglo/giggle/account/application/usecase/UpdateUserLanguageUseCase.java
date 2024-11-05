package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.application.dto.request.UpdateUserLanguageRequestDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface UpdateUserLanguageUseCase {

    /**
     * 유저 언어 변경하기
     *
     * @param accountId 계정 ID
     * @param requestDto 유저 언어 변경하기
     */
    void execute(UUID accountId, UpdateUserLanguageRequestDto requestDto);
}
