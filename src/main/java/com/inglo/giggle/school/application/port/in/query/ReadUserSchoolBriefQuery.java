package com.inglo.giggle.school.application.port.in.query;

import com.inglo.giggle.school.application.port.in.result.ReadUserSchoolBriefResult;

public interface ReadUserSchoolBriefQuery {

    /**
     * 사용자 학교 간단 정보 읽기 쿼리
     * @param search 검색어
     * @param page 페이지네이션에서 사용되는 페이지 번호
     * @param size 페이지네이션에서 사용되는 페이지 크기
     * @return ReadUserSchoolBriefResponseDto 사용자 학교 간단 정보
     */
    ReadUserSchoolBriefResult execute(String search, Integer page, Integer size);
}
