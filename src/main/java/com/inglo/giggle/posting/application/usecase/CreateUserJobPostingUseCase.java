package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.application.dto.response.CreateUserJobPostingResponseDto;

import java.util.UUID;

@UseCase
public interface CreateUserJobPostingUseCase {

    /**
     * 4.9 (유학생) 공고 지원하기
     * @param accountId 계정 ID
     * @param jobPostingId 공고 ID
     */
    CreateUserJobPostingResponseDto execute(
            UUID accountId,
            Long jobPostingId
    );

}
