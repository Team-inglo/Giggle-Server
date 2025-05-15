package com.inglo.giggle.resume.controller.query;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.resume.application.dto.response.ReadAdminResumeDetailResponseDtoV2;
import com.inglo.giggle.resume.application.usecase.ReadAdminResumeDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/admins/resumes")
public class ResumeAdminsQueryV2Controller {

    private final ReadAdminResumeDetailUseCase readAdminResumeDetailUseCase;

    @GetMapping("/{resumeId}")
    public ResponseDto<ReadAdminResumeDetailResponseDtoV2> readAdminResumeDetail(
            @PathVariable("resumeId") UUID resumeId
    ) {
        return ResponseDto.ok(readAdminResumeDetailUseCase.executeV2(resumeId));
    }
}
