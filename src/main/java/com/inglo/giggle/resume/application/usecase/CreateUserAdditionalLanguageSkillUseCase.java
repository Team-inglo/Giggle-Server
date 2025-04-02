package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.presentation.dto.request.CreateUserAdditionalLanguageSkillRequestDto;

import java.util.UUID;

@UseCase
public interface CreateUserAdditionalLanguageSkillUseCase {

    /**
     * (유학생) 언어 - ETC 생성하기 유스케이스
     * @param accountId 계정 ID
     * @param requestDto 언어 - ETC 생성하기 요청 DTO
     */
    void execute(UUID accountId, CreateUserAdditionalLanguageSkillRequestDto requestDto);
}
