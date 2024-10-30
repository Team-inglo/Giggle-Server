package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.UpdateUserSocialIntegrationProgramReqeustDto;

import java.util.UUID;

@UseCase
public interface UpdateUserSocialIntegrationProgramUseCase {
    void execute(UUID accountId, UpdateUserSocialIntegrationProgramReqeustDto requestDto);
}
