package com.inglo.giggle.school.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.school.application.dto.request.UpdateAdminSchoolRequestDto;

@UseCase
public interface UpdateAdminSchoolUseCase {
    /**
     * 9.6 (어드민) 학교 수정하기 UseCase
     */
    void execute(Long schoolId, UpdateAdminSchoolRequestDto requestDto);
}
