package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.application.dto.response.ReadUserOwnerJobPostingListResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserOwnerJobPostingListUseCase {

    /**
     * 유저가 지원한 채용공고 리스트를 조회합니다.
     *
     * @param accountId 계정 ID
     * @param page 페이지
     * @param size 사이즈
     * @param sortingType 정렬 기준
     * @param status 현재 진행 상태
     *
     * @return 유저가 지원한 채용공고 리스트
     */
    ReadUserOwnerJobPostingListResponseDto execute(
            UUID accountId,
            Integer page,
            Integer size,
            String sortingType,
            String status
    );
}