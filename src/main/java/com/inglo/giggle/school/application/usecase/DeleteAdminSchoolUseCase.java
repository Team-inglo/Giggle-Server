package com.inglo.giggle.school.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

@UseCase
public interface DeleteAdminSchoolUseCase {
    /**
     * 9.7 (어드민) 학교 삭제하기 UseCase
     */
    void execute(Long schoolId);
}
