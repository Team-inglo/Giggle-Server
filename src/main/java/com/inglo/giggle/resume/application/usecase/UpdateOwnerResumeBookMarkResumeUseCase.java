package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.response.UpdateOwnerResumeBookMarkResumeResponseDto;

import java.util.UUID;

@UseCase
public interface UpdateOwnerResumeBookMarkResumeUseCase {

    UpdateOwnerResumeBookMarkResumeResponseDto execute(
            UUID ownerId,
            UUID resumeId
    );
}

