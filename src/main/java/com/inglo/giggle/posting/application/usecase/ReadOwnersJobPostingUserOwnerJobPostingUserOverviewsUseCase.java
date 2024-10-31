package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.application.dto.response.ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto;

import java.util.UUID;

@UseCase
public interface ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsUseCase {

    /**
     * (고용주) 공고에 대한 지원자 리스트 조회
     *
     * @param accountId 계정 ID
     * @param jobPostingId 채용공고 ID
     * @param page 페이지
     * @param size 사이즈
     * @param sorting 정렬
     * @param status 상태
     *
     * @return 채용공고 사용자 개요
     */
    ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto execute(
            UUID accountId,
            Long jobPostingId,
            Integer page,
            Integer size,
            String sorting,
            String status
    );
}
