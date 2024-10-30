package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.response.ReadOwnerResumeDetailResponseDto;

@UseCase
public interface ReadOwnerResumeDetailUseCase {
    ReadOwnerResumeDetailResponseDto execute(Long userOwnerJobPostingId);
}
