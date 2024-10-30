package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.application.dto.response.ReadUserJobPostingBriefResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserJobPostingBriefUseCase {

    /**
     * 유저의 채용공고 간략 조회
     *
     * @param userId 유저 ID
     * @return 채용공고 간략 조회 응답 DTO
     */
    ReadUserJobPostingBriefResponseDto execute(
            UUID userId
    );
}
