package com.inglo.giggle.resume.application.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.resume.application.dto.response.ReadUserEducationDetailResponseDto;
import com.inglo.giggle.resume.application.dto.response.ReadUserResumeDetailResponseDto;
import com.inglo.giggle.resume.application.dto.response.ReadUserWorkExperienceDetailResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadUserEducationDetailUseCase;
import com.inglo.giggle.resume.application.usecase.ReadUserResumeDetailUseCase;
import com.inglo.giggle.resume.application.usecase.ReadUserWorkExperienceDetailUseCase;
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

    /** 
     * 7.1 (유학생) 이력서 조회하기
     */
    @GetMapping("/details")
    public ResponseDto<ReadUserResumeDetailResponseDto> readUserResumeDetail(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserResumeDetailUseCase.execute(accountId));
    }

    /**
     * 7.2 (유학생) 경력 상세 조회하기
     */
    @GetMapping("/work-experiences/{id}/details")
    public ResponseDto<ReadUserWorkExperienceDetailResponseDto> readUserWorkExperienceDetail(
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readUserWorkExperienceDetailUseCase.execute(id));
    }

    /**
     * 7.3 (유학생) 학력 상세 조회하기
     */
    @GetMapping("/educations/{id}/details")
    public ResponseDto<ReadUserEducationDetailResponseDto> readUserEducationDetail(
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readUserEducationDetailUseCase.execute(id));
    }

}
