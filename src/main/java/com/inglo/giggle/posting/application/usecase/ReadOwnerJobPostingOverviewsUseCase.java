package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.application.dto.response.ReadOwnerJobPostingOverviewsResponseDto;

import java.util.UUID;

@UseCase
public interface ReadOwnerJobPostingOverviewsUseCase {

    /**
     * 6.6 (고용주) 등록한 공고 리스트 조회하기
     *
     * @param accountId 계정 ID
     * @param page 페이지
     * @param size 사이즈
     *
     * @return 고용주가 등록한 공고 리스트
     */
    ReadOwnerJobPostingOverviewsResponseDto execute(
            UUID accountId,
            Integer page,
            Integer size,
            String sorting
    );
}
