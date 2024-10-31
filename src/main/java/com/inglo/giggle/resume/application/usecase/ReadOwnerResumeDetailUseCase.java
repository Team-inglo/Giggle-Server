package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.response.ReadOwnerResumeDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadOwnerResumeDetailUseCase {
    ReadOwnerResumeDetailResponseDto execute(UUID accountId, Long userOwnerJobPostingId);
}
