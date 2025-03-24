package com.inglo.giggle.school.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.school.application.dto.response.ReadAdminSchoolDetailResponseDto;

@UseCase
public interface ReadAdminSchoolDetailUseCase {
    /**
     * 9.4 (어드민) 학교 상세 조회하기 UseCase
     */
    ReadAdminSchoolDetailResponseDto execute(Long id);
}
