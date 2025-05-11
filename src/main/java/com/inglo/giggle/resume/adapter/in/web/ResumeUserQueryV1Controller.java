package com.inglo.giggle.resume.adapter.in.web;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.resume.application.port.in.query.ReadUserEducationDetailQuery;
import com.inglo.giggle.resume.application.port.in.query.ReadUserLanguageSummaryQuery;
import com.inglo.giggle.resume.application.port.in.query.ReadUserResumeDetailQuery;
import com.inglo.giggle.resume.application.port.in.query.ReadUserWorkExperienceDetailQuery;
import com.inglo.giggle.resume.application.port.in.result.ReadUserEducationDetailResult;
import com.inglo.giggle.resume.application.port.in.result.ReadUserLanguageSummaryResult;
import com.inglo.giggle.resume.application.port.in.result.ReadUserResumeDetailResult;
import com.inglo.giggle.resume.application.port.in.result.ReadUserWorkExperienceDetailResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/resumes")
public class ResumeUserQueryV1Controller {

    private final ReadUserResumeDetailQuery readUserResumeDetailQuery;
    private final ReadUserWorkExperienceDetailQuery readUserWorkExperienceDetailQuery;
    private final ReadUserEducationDetailQuery readUserEducationDetailQuery;
    private final ReadUserLanguageSummaryQuery readUserLanguageSummaryQuery;

    /**
     * 7.1 (유학생) 이력서 조회하기
     */
    @GetMapping("/details")
    public ResponseDto<ReadUserResumeDetailResult> readUserResumeDetail(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserResumeDetailQuery.execute(accountId));
    }

    /**
     * 7.2 (유학생) 경력 상세 조회하기
     */
    @GetMapping("/work-experiences/{id}/details")
    public ResponseDto<ReadUserWorkExperienceDetailResult> readUserWorkExperienceDetail(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readUserWorkExperienceDetailQuery.execute(accountId, id));
    }

    /**
     * 7.3 (유학생) 학력 상세 조회하기
     */
    @GetMapping("/educations/{id}/details")
    public ResponseDto<ReadUserEducationDetailResult> readUserEducationDetail(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readUserEducationDetailQuery.execute(accountId, id));
    }

    /**
     * 7.4 (유학생) 언어 요약 조회하기
     */
    @GetMapping("/languages/summaries")
    public ResponseDto<ReadUserLanguageSummaryResult> readUserLanguageSummary(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserLanguageSummaryQuery.execute(accountId));
    }

}
