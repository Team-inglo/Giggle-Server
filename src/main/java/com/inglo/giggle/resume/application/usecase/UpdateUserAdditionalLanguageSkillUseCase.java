package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.UpdateUserAdditionalLanguageSkillRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateUserAdditionalLanguageSkillUseCase {

    /**
     * (유학생) 언어 - ETC 수정하기 유스케이스
     * @param accountId 계정 ID
     * @param additionalLanguageSkillId 언어 - ETC ID
     * @param requestDto 언어 - ETC 수정 요청 DTO
     */
    void execute(UUID accountId, Long additionalLanguageSkillId, UpdateUserAdditionalLanguageSkillRequestDto requestDto);
}
