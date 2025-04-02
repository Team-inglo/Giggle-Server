package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.presentation.dto.response.ReadOwnerUserOwnerJobPostingUserBriefResponseDto;

import java.util.UUID;

@UseCase
public interface ReadOwnerUserOwnerJobPostingUserBriefUseCase {

    /**
     * 6.5 (고용주) 지원자 간단 정보 조회하기
     *
     * @param accountId 계정 ID
     * @param userOwnerJobPostingsId 유저가 지원한 채용공고 ID
     *
     * @return 지원자 간단정보 DTO
     */
    ReadOwnerUserOwnerJobPostingUserBriefResponseDto execute(
            UUID accountId,
            Long userOwnerJobPostingsId
    );
}
