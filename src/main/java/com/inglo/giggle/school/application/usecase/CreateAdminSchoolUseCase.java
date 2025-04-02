package com.inglo.giggle.school.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.school.presentation.dto.request.CreateAdminSchoolRequestDto;

@UseCase
public interface CreateAdminSchoolUseCase {

    /**
     * 9.5 (어드민) 학교 생성하기 UseCase
     */
    void execute(CreateAdminSchoolRequestDto requestDto);
}
