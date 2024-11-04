package com.inglo.giggle.posting.application.usecase;


import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.application.dto.response.ReadUserOwnerJobPostingBriefListResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserUserOwnerJobPostingBriefListUseCase {

    /**
     * 유저가 현재 진행중인 인터뷰 리스트를 조회합니다.
     *
     * @param accountId 계정 ID
     * @param page 페이지
     * @param size 사이즈
     *
     * @return 유저가 현재 진행중인 인터뷰 리스트
     */
    ReadUserOwnerJobPostingBriefListResponseDto execute(
            UUID accountId,
            Integer page,
            Integer size
    );
}