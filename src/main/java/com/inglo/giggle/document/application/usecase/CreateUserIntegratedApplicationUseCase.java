package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.presentation.dto.request.CreateUserIntegratedApplicationRequestDto;

import java.util.UUID;

@UseCase
public interface CreateUserIntegratedApplicationUseCase {

    /**
     * (유학생) 통합 지원서 생성하기
     *
     * @param accountId 계정 ID
     * @param userOwnerJobPostingId 유저가 지원한 공고 ID
     * @param requestDto (유학생) 통합 지원서 생성하기
     */
    void execute(UUID accountId, Long userOwnerJobPostingId, CreateUserIntegratedApplicationRequestDto requestDto);
}
