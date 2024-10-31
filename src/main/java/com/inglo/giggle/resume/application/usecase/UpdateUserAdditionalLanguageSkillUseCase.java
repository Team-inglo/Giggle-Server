package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.UpdateUserAdditionalLanguageSkillRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateUserAdditionalLanguageSkillUseCase {
    void execute(UUID accountId, Long additionalLanguageSkillId, UpdateUserAdditionalLanguageSkillRequestDto requestDto);
}
