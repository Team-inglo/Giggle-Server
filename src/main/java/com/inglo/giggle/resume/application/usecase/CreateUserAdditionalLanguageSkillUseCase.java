package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.CreateUserAdditionalLanguageSkillRequestDto;

import java.util.UUID;

@UseCase
public interface CreateUserAdditionalLanguageSkillUseCase {
    void execute(UUID accountId, CreateUserAdditionalLanguageSkillRequestDto requestDto);
}
