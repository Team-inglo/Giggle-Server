package com.inglo.giggle.resume.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.resume.application.dto.response.*;
import com.inglo.giggle.resume.application.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/resumes")
public class ResumeUsersQueryV1Controller {
    private final ReadUserResumeDetailUseCase readUserResumeDetailUseCase;
    private final ReadUserWorkExperienceDetailUseCase readUserWorkExperienceDetailUseCase;
    private final ReadUserEducationDetailUseCase readUserEducationDetailUseCase;
    private final ReadUserLanguageSummaryUseCase readUserLanguageSummaryUseCase;
    private final ReadUserWorkPreferenceDetailUseCase readUserWorkPreferenceDetailUseCase;
    private final ReadUserResumeCompletionRateUseCase readUserResumeCompletionRateUseCase;

    /** 
     * 7.1 (유학생) 이력서 조회하기
     */
    @GetMapping("/details")
    public ResponseDto<ReadUserResumeDetailResponseDtoV1> readUserResumeDetail(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserResumeDetailUseCase.execute(accountId));
    }

    /**
     * 7.2 (유학생) 경력 상세 조회하기
     */
    @GetMapping("/work-experiences/{id}/details")
    public ResponseDto<ReadUserWorkExperienceDetailResponseDto> readUserWorkExperienceDetail(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readUserWorkExperienceDetailUseCase.execute(accountId, id));
    }

    /**
     * 7.3 (유학생) 학력 상세 조회하기
     */
    @GetMapping("/educations/{id}/details")
    public ResponseDto<ReadUserEducationDetailResponseDto> readUserEducationDetail(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readUserEducationDetailUseCase.execute(accountId, id));
    }

    /**
     * 7.4 (유학생) 언어 요약 조회하기
     */
    @GetMapping("/languages/summaries")
    public ResponseDto<ReadUserLanguageSummaryResponseDto> readUserLanguageSummary(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserLanguageSummaryUseCase.execute(accountId));
    }

    /**
     * 7.21 (유학생) 희망 근로 조건 상세 조회하기
     */
    @GetMapping("/work-preferences/details")
    public ResponseDto<ReadUserWorkPreferenceDetailResponseDto> readUserWorkPreferenceDetail(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserWorkPreferenceDetailUseCase.execute(accountId));
    }

    /**
     * 7.26 (유학생) 이력서 완성도 조회하기
     */
    @GetMapping("/completion-rate")
    public ResponseDto<ReadUserResumeCompletionRateResponseDto> readUserResumeCompletionRate(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserResumeCompletionRateUseCase.execute(accountId));
    }
}
