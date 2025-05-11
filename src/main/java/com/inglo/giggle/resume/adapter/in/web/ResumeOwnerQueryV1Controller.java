package com.inglo.giggle.resume.adapter.in.web;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.resume.application.port.in.result.ReadOwnerResumeDetailResult;
import com.inglo.giggle.resume.application.port.in.query.ReadOwnerResumeDetailQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/owners")
public class ResumeOwnerQueryV1Controller {
    private final ReadOwnerResumeDetailQuery readOwnerResumeDetailQuery;

    /**
     * 7.19 (고용주) 이력서 조회하기
     */
    @GetMapping("/user-owner-job-postings/{id}/users/resumes/details")
    public ResponseDto<ReadOwnerResumeDetailResult> readOwnerResumeDetail(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readOwnerResumeDetailQuery.execute(accountId, id));
    }
}
