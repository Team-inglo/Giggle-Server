package com.inglo.giggle.school.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.school.application.dto.response.ReadUserSchoolBriefResponseDto;

@UseCase
public interface ReadUserSchoolBriefUseCase {
    ReadUserSchoolBriefResponseDto execute(String search, Integer page, Integer size);
}
