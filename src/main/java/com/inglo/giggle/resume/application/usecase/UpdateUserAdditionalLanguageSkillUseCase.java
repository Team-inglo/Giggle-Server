package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.UpdateUserAdditionalLanguageSkillRequestDto;

@UseCase
public interface UpdateUserAdditionalLanguageSkillUseCase {
    void execute(Long additionalLanguageSkillId, UpdateUserAdditionalLanguageSkillRequestDto requestDto);
}
