package com.inglo.giggle.resume.application.port.in.query;

import com.inglo.giggle.resume.application.port.in.result.ReadOwnerResumeDetailResult;

import java.util.UUID;

public interface ReadOwnerResumeDetailQuery {

    /**
     * (고용주) 이력서 상세 조회하기 유스케이스
     * @param accountId 계정 ID
     * @param userOwnerJobPostingId 이력서 ID
     * @return 이력서 상세 조회하기 응답 DTO
     */
    ReadOwnerResumeDetailResult execute(UUID accountId, Long userOwnerJobPostingId);
}
