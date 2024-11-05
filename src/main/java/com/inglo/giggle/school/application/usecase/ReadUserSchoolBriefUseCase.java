package com.inglo.giggle.school.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.school.application.dto.response.ReadUserSchoolBriefResponseDto;

@UseCase
public interface ReadUserSchoolBriefUseCase {

    /**
     * 사용자 학교 간단 정보 읽기 유스케이스
     * @param search 검색어
     * @param page 페이지네이션에서 사용되는 페이지 번호
     * @param size 페이지네이션에서 사용되는 페이지 크기
     * @return ReadUserSchoolBriefResponseDto 사용자 학교 간단 정보
     */
    ReadUserSchoolBriefResponseDto execute(String search, Integer page, Integer size);
}
