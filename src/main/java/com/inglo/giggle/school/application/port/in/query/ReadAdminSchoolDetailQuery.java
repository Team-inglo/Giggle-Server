package com.inglo.giggle.school.application.port.in.query;

import com.inglo.giggle.school.application.port.in.result.ReadAdminSchoolDetailResult;

public interface ReadAdminSchoolDetailQuery {
    /**
     * 9.4 (어드민) 학교 상세 조회 쿼리
     */
    ReadAdminSchoolDetailResult execute(Long id);
}
