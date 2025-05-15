package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.CreateUserWorkPreferenceRequestDto;

import java.util.UUID;

@UseCase
public interface CreateUserWorkPreferenceUseCase {

    /**
     * 7.22 (유학생) 희망 근로 조건 생성하기
     */
    void execute(UUID accountId, CreateUserWorkPreferenceRequestDto requestDto);
}
