package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.application.dto.response.ReadUserAppliedJobDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserAppliedJobDetailUseCase {

    /**
     * 유저가 지원한 채용공고 디테일을 조회합니다.
     *
     * @param accountId 계정 ID
     * @param userOwnerJobPostingsId 유저가 지원한 채용공고 ID
     *
     * @return 유저가 지원한 채용공고 디테일
     */
    ReadUserAppliedJobDetailResponseDto execute(
            UUID accountId,
            Long userOwnerJobPostingsId
    );
}