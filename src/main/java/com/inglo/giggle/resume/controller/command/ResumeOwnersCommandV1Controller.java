package com.inglo.giggle.resume.controller.command;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.resume.application.dto.response.UpdateOwnerResumeBookMarkResumeResponseDto;
import com.inglo.giggle.resume.application.usecase.UpdateOwnerResumeBookMarkResumeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/owners/resumes")
public class ResumeOwnersCommandV1Controller {

    private final UpdateOwnerResumeBookMarkResumeUseCase updateOwnerResumeBookMarkResumeUseCase;

    /**
     * 15.1 (고용주) 인재 스크랩 추가/삭제
     */
    @PutMapping("{resume-id}/book-mark-resumes")
    public ResponseDto<UpdateOwnerResumeBookMarkResumeResponseDto> updateOwnerResumeBookMarkResume(
            @AccountID UUID ownerId,
            @PathVariable("resume-id") UUID resumeId
    ) {
        return ResponseDto.ok(
                updateOwnerResumeBookMarkResumeUseCase.execute(
                        ownerId,
                        resumeId
                )
        );
    }

}
