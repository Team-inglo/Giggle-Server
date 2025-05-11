package com.inglo.giggle.school.application.port.in.query;

import com.inglo.giggle.school.application.port.in.result.ReadAdminSchoolOverviewResult;

public interface ReadAdminSchoolOverviewQuery {
    /**
     * 9.3 (어드민) 학교 목록 조회 쿼리
     */
    ReadAdminSchoolOverviewResult execute(Integer page, Integer size, String search);
}
