package com.inglo.giggle.resume.application.port.in.query;

import com.inglo.giggle.resume.application.port.in.result.ReadAdminResumeDetailResult;
import com.inglo.giggle.security.info.CustomUserPrincipal;

import java.util.UUID;

public interface ReadAdminResumeDetailQuery {
    /**
     * 7.20 (어드민) 이력서 조회하기
     */
    ReadAdminResumeDetailResult execute(UUID resumeId);
}
