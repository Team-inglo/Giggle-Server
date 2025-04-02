package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.presentation.dto.response.ReadUserOwnerJobPostingJobPostingRecruiterResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserUserOwnerJobPostingJobPostingRecruiterUserCase {

    /**
     * 유저가 지원한 공고의 담당자 정보 조회하기
     *
     * @param accountId 계정 ID
     * @param userOwnerJobPostingId 채용공고 ID
     *
     * @return 유저가 지원한 공고의 담당자 정보 DTO
     */
    ReadUserOwnerJobPostingJobPostingRecruiterResponseDto execute(
            UUID accountId,
            Long userOwnerJobPostingId
    );
}
