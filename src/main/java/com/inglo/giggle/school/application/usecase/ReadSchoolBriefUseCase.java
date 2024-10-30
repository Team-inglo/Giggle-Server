package com.inglo.giggle.school.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.school.application.dto.response.ReadSchoolBriefResponseDto;

@UseCase
public interface ReadSchoolBriefUseCase {
    ReadSchoolBriefResponseDto execute(String search, Integer page, Integer size);
}
