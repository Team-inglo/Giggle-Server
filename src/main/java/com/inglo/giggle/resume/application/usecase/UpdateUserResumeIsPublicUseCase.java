package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.response.UpdateUserResumeIsPublicResponseDto;

import java.util.UUID;

@UseCase
public interface UpdateUserResumeIsPublicUseCase {

    UpdateUserResumeIsPublicResponseDto execute(
            boolean isPublic,
            UUID resumeId
    );
}
