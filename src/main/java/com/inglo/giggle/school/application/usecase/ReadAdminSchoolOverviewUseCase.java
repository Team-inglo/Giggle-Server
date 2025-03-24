package com.inglo.giggle.school.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.school.application.dto.response.ReadAdminSchoolOverviewResponseDto;

@UseCase
public interface ReadAdminSchoolOverviewUseCase {
    /**
     * 9.3 (어드민) 학교 목록 조회하기
     */
    ReadAdminSchoolOverviewResponseDto execute(Integer page, Integer size, String search);
}
