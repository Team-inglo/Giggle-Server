package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.presentation.dto.response.ReadUserBookMarkOverviewResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserBookMarkOverviewUseCase {

    /**
     * 4.8 (유학생) 북마크한 공고 리스트 조회하기
     * @param accountId 계정 ID
     * @param page 페이지 번호
     * @param size 페이지 크기
     *
     * @return 북마크한 공고 리스트 조회 결과
     */
    ReadUserBookMarkOverviewResponseDto execute(
            UUID accountId,
            Integer page,
            Integer size
    );
}
