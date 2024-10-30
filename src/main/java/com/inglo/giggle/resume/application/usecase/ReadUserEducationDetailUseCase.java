package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.response.ReadUserEducationDetailResponseDto;

@UseCase
public interface ReadUserEducationDetailUseCase {
    ReadUserEducationDetailResponseDto execute(Long educationId);

}
