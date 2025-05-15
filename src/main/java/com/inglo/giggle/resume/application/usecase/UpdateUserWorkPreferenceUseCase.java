package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.UpdateUserWorkPreferenceRequestDto;

@UseCase
public interface UpdateUserWorkPreferenceUseCase {

    /**
     * 7.23 (유학생) 희망 근로 조건 수정하기
     */
    void execute(Long workPreferenceId, UpdateUserWorkPreferenceRequestDto requestDto);
}
