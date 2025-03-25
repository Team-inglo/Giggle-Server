package com.inglo.giggle.resume.controller.query;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.resume.application.dto.response.ReadAdminResumeDetailResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadAdminResumeDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins/resumes")
public class ResumeAdminsQueryV1Controller {

    private final ReadAdminResumeDetailUseCase readAdminResumeDetailUseCase;

    @GetMapping("/{resumeId}")
    public ResponseDto<ReadAdminResumeDetailResponseDto> readAdminResumeDetail(
            @PathVariable("resumeId") UUID resumeId
    ) {
        return ResponseDto.ok(readAdminResumeDetailUseCase.execute(resumeId));
    }
}
